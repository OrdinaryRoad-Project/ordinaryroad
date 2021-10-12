package top.ordinaryroad.auth.authenticate.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import top.ordinaryroad.auth.authenticate.service.SysUserDetailsServiceImpl;

/**
 * AuthenticationProvider提供登录验证处理逻辑，我们实现该接口编写自己的验证逻辑。
 *
 * @author qq1962247851
 * @date 2020/4/21 18:14
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private SysUserDetailsServiceImpl userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 通过authentication.getDetails()获取详细信息
        WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
        // 获取用户输入的用户名和密码
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        // userDetails为数据库中查询到的用户信息
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        // 如果是自定义AuthenticationProvider，需要手动密码校验
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("密码错误");
        }
        return new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
