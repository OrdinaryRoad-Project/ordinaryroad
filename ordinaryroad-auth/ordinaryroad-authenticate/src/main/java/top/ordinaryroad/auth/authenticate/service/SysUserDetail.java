package top.ordinaryroad.auth.authenticate.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import top.ordinaryroad.system.entity.SysUser;

import java.util.Collection;

public class SysUserDetail implements UserDetails {
    private static final long serialVersionUID = -1292469610493518614L;
    private Collection<? extends GrantedAuthority> authorities;
    private SysUser sysUser;

    public SysUserDetail(SysUser sysUser, Collection<? extends GrantedAuthority> authorities) {
        this.sysUser = sysUser;
        this.authorities = authorities;
    }

    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return sysUser.getPassword();
    }

    @Override
    public String getUsername() {
        return sysUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return sysUser.getNotExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return sysUser.getNotLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return sysUser.getPasswordNotExpired();
    }

    @Override
    public boolean isEnabled() {
        return sysUser.getEnabled();
    }
}