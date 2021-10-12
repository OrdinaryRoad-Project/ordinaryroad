package top.ordinaryroad.auth.authenticate.service;

import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import top.ordinaryroad.auth.authenticate.exception.NullUserNameException;
import top.ordinaryroad.commons.core.base.result.Result;
import top.ordinaryroad.system.RemoteUserService;
import top.ordinaryroad.system.entity.SysUser;

import java.util.Collections;
import java.util.Objects;

@Slf4j
@Component
public class SysUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private RemoteUserService userService;

//    private final SysUserService sysUserService;
//    private final ISysRoleService sysRoleService;
//    private final SysPermissionService sysPermissionService;
//    private final SysUserRoleRelationService sysUserRoleRelationService;
//    private final SysRolePermissionRelationService sysRolePermissionRelationService;

    @Bean
    public PasswordEncoder passwordEncoder() {
//        // 设置默认的加密方式（强hash方式加密）
//        // password: {bcrypt}$2a$10$md4Fe1LzbZMryJOSdgm5.Ol9b1YtQKk0KdQPAX6ojt4QYnjezwmY2
//        // 123456: {bcrypt}$2a$10$Uyw/T0Z/HICS.bTKz68yKurhyGEBZCWlbnYtNawzbmioDT/8B.Tna
//        // admin: {bcrypt}$2a$10$Wjx1Dj04UdRbdLz7Z36sEOdNLprCDWRl7xaz9jK1Ji6cTm8W2lFNC
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (StringUtil.isNullOrEmpty(username)) {
            throw new NullUserNameException("用户名不能为空");
        }

        Result<SysUser> userInfo = userService.getUserInfo(username);
        SysUser data = userInfo.getData();
        if (Objects.nonNull(data)) {
            return new SysUserDetail(data, Collections.emptyList());
        } else {
            throw new UsernameNotFoundException("未找到用户名为：" + username + "的用户");
        }

        /**
         // TODO 根据用户名查询用户
         SysUser sysUser = sysUserService.selectByUserName(username);
         if (sysUser == null) {
         throw new InternalAuthenticationServiceException("账号不存在");
         }
         List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
         List<SysUserRoleRelation> sysUserRoleRelations = sysUserRoleRelationService.queryAllByUserId(sysUser.getId());
         for (SysUserRoleRelation sysUserRoleRelation : sysUserRoleRelations) {
         SysRole sysRole = sysRoleService.queryById(sysUserRoleRelation.getRoleId());
         List<SysRolePermissionRelation> sysRolePermissionRelation = sysRolePermissionRelationService.queryAllByRoleId(sysRole.getId());
         for (SysRolePermissionRelation rolePermissionRelation : sysRolePermissionRelation) {
         SysPermission sysPermission = sysPermissionService.queryById(rolePermissionRelation.getPermissionId());
         GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(sysPermission.getCode());
         grantedAuthorities.add(grantedAuthority);
         }
         }
         //获取该用户所拥有的权限
         //        List<SysPermission> sysPermissions = sysPermissionService.selectListByUser(sysUser.getId());
         // 声明用户授权
         //        sysPermissions.forEach(sysPermission -> {
         //            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(sysPermission.getPermissionCode());
         //            grantedAuthorities.add(grantedAuthority);
         //        });
         return new SysUserDetail(sysUser, grantedAuthorities);
         **/
//        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
//        grantedAuthorities.add()
    }

}
