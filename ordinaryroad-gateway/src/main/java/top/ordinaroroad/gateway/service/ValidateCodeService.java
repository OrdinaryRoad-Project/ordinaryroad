package top.ordinaroroad.gateway.service;

import top.ordinaryroad.commons.core.base.exception.CaptchaException;
import top.ordinaryroad.commons.core.base.result.Result;

import java.io.IOException;

/**
 * 验证码处理
 *
 * @author ruoyi
 */
public interface ValidateCodeService {

    /**
     * 生成验证码
     */
    Result<?> createCapcha() throws IOException, CaptchaException;

    /**
     * 校验验证码
     */
    void checkCapcha(String key, String value) throws CaptchaException;

}
