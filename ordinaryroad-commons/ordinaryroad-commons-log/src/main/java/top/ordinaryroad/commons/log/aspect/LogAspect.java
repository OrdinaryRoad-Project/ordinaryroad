package top.ordinaryroad.commons.log.aspect;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import top.ordinaryroad.commons.core.utils.ExceptionUtil;
import top.ordinaryroad.commons.core.utils.SecurityUtils;
import top.ordinaryroad.commons.core.utils.ServletUtils;
import top.ordinaryroad.commons.core.utils.StringUtils;
import top.ordinaryroad.commons.core.utils.ip.IpUtils;
import top.ordinaryroad.commons.log.annotation.Log;
import top.ordinaryroad.commons.log.enums.BusinessStatus;
import top.ordinaryroad.commons.log.service.AsyncLogService;
import top.ordinaryroad.system.entity.SysOperLogDO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * 操作日志记录处理
 *
 * @author ruoyi
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    @Autowired
    private AsyncLogService asyncLogService;

    /**
     * 配置织入点
     */
    @Pointcut("@annotation(top.ordinaryroad.commons.log.annotation.Log)")
    public void logPointCut() {
    }

    @Before("logPointCut()")
    public void doBefore(JoinPoint joinPoint) {
        log.debug("==========doBefore==========");
        SysOperLogDO operLog = initBasicOperLog(joinPoint);
        log.debug("来源IP：{}", operLog.getOperIp());
        log.debug("请求的地址：{}", operLog.getOperUrl());
        log.debug("请求方式：{}", operLog.getRequestMethod());
        log.debug("请求用户：{}", operLog.getOperName());
        log.debug("被请求的类和方法：{}", operLog.getMethod());
        log.debug("请求参数：{}", operLog.getOperParam());
    }

    @After("logPointCut()")
    public void doAfter() {
        log.debug("==========doAfter==========");
    }

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "logPointCut()", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Object jsonResult) {
        handleLog(joinPoint, null, jsonResult);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e         异常
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        handleLog(joinPoint, e, null);
    }

    protected void handleLog(final JoinPoint joinPoint, final Exception e, Object jsonResult) {
        // *========数据库日志=========*//
        try {
            // 获得注解
            Log controllerLog = getAnnotationLog(joinPoint);
            if (controllerLog == null) {
                return;
            }

            SysOperLogDO operLog = initBasicOperLog(joinPoint);

            // 返回参数
            operLog.setJsonResult(JSON.toJSONString(jsonResult));

            if (e != null) {
                operLog.setStatus(BusinessStatus.FAIL.ordinal());
                operLog.setErrorMsg(StringUtils.substring(e.getMessage(), 0, 2000));
                e.printStackTrace();
            } else {
                operLog.setStatus(BusinessStatus.SUCCESS.ordinal());
            }

            // 处理设置注解上的参数
            getControllerMethodDescription(joinPoint, controllerLog, operLog);
            // TODO 保存数据库
            // asyncLogService.saveSysLog(operLog);
            log.debug("handleLog {}", operLog);
        } catch (Exception exp) {
            // 记录本地异常日志
            log.error("异常信息:{}", ExceptionUtil.getMessage(exp));
            ExceptionUtil.printRootCauseStackTrace(exp);
        }
    }

    /**
     * 初始化基础OperLog
     * 来源IP
     * 请求的地址
     * 请求方式
     * 请求用户
     * 被请求的类和方法
     * 请求参数
     *
     * @param joinPoint JoinPoint
     * @return SysOperLogDO
     */
    private SysOperLogDO initBasicOperLog(JoinPoint joinPoint) {
        SysOperLogDO operLog = new SysOperLogDO();
        HttpServletRequest request = ServletUtils.getRequest();
        if (request != null) {
            // 设置来源IP
            operLog.setOperIp(IpUtils.getIpAddr(request));
            // 设置请求的地址
            operLog.setOperUrl(request.getRequestURI());
            // 设置请求方式
            operLog.setRequestMethod(request.getMethod());
            // 设置请求用户
            operLog.setOperName(SecurityUtils.getUsername(request));
        }
        // 设置被请求的类和方法
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        operLog.setMethod(className + "." + methodName + "()");
        // 设置请求参数
        setRequestValue(joinPoint, operLog);
        return operLog;
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param log     日志
     * @param operLog 操作日志
     * @throws Exception
     */
    public void getControllerMethodDescription(JoinPoint joinPoint, Log log, SysOperLogDO operLog) throws Exception {
        // 设置action动作
        operLog.setBusinessType(log.businessType().ordinal());
        // 设置标题
        operLog.setTitle(log.title());
        // 设置操作人类别
        operLog.setOperatorType(log.operatorType().ordinal());
        // 是否需要保存request，参数和值
        if (!log.isSaveRequestData()) {
            operLog.setOperParam(null);
        }
    }

    /**
     * 获取PUT和POST方式请求的参数，放到log中
     *
     * @param operLog 操作日志
     */
    private void setRequestValue(JoinPoint joinPoint, SysOperLogDO operLog) {
        String requestMethod = operLog.getRequestMethod();
        if (HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod)) {
            String params = argsArrayToString(joinPoint.getArgs());
            operLog.setOperParam(StringUtils.substring(params, 0, 2000));
        }
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private Log getAnnotationLog(JoinPoint joinPoint) throws Exception {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null) {
            return method.getAnnotation(Log.class);
        }
        return null;
    }

    /**
     * 参数拼装
     */
    private String argsArrayToString(Object[] paramsArray) {
        String params = "";
        if (paramsArray != null && paramsArray.length > 0) {
            for (int i = 0; i < paramsArray.length; i++) {
                if (StringUtils.isNotNull(paramsArray[i]) && !isFilterObject(paramsArray[i])) {
                    try {
                        Object jsonObj = JSON.toJSON(paramsArray[i]);
                        params += jsonObj.toString() + " ";
                    } catch (Exception e) {
                    }
                }
            }
        }
        return params.trim();
    }

    /**
     * 判断是否需要过滤的对象。
     *
     * @param o 对象信息。
     * @return 如果是需要过滤的对象，则返回true；否则返回false。
     */
    @SuppressWarnings("rawtypes")
    public boolean isFilterObject(final Object o) {
        Class<?> clazz = o.getClass();
        if (clazz.isArray()) {
            return clazz.getComponentType().isAssignableFrom(MultipartFile.class);
        } else if (Collection.class.isAssignableFrom(clazz)) {
            Collection collection = (Collection) o;
            for (Iterator iter = collection.iterator(); iter.hasNext(); ) {
                return iter.next() instanceof MultipartFile;
            }
        } else if (Map.class.isAssignableFrom(clazz)) {
            Map map = (Map) o;
            for (Iterator iter = map.entrySet().iterator(); iter.hasNext(); ) {
                Map.Entry entry = (Map.Entry) iter.next();
                return entry.getValue() instanceof MultipartFile;
            }
        }
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse
                || o instanceof BindingResult;
    }
}
