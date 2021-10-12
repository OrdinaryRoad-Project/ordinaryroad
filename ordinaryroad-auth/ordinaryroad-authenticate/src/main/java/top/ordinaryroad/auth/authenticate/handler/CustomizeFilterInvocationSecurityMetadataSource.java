package top.ordinaryroad.auth.authenticate.handler;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

@Component
public class CustomizeFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    //    AntPathMatcher antPathMatcher = new AntPathMatcher();
    /**
    private final SysPermissionService sysPermissionService;
    private final SysRequestPathService sysRequestPathService;
    private final SysRequestPathPermissionRelationService sysRequestPathPermissionRelationService;
     */

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        //获取请求地址
        String requestUrl = ((FilterInvocation) o).getRequestUrl();
        if (requestUrl.contains("?")) {
            requestUrl = requestUrl.substring(0, requestUrl.indexOf("?"));
        }
        /*
        TODO 查询具体某个接口的权限
        /sport/query
        */
        /**
        SysRequestPath sysRequestPath = sysRequestPathService.queryByUrl(requestUrl);
        if (sysRequestPath == null) {
            return Collections.emptyList();
        }
        List<SysRequestPathPermissionRelation> sysRequestPathPermissionRelations = sysRequestPathPermissionRelationService.queryListByUrlId(sysRequestPath.getId());
        if (sysRequestPathPermissionRelations.isEmpty()) {
            return Collections.emptyList();
        }
        String[] attributes = new String[sysRequestPathPermissionRelations.size()];
        for (int i = 0; i < sysRequestPathPermissionRelations.size(); i++) {
            SysRequestPathPermissionRelation sysRequestPathPermissionRelation = sysRequestPathPermissionRelations.get(i);
            SysPermission sysPermission = sysPermissionService.queryById(sysRequestPathPermissionRelation.getPermissionId());
            attributes[i] = sysPermission.getCode();
        }
        return SecurityConfig.createList(attributes);
        */
        return Collections.emptyList();
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
