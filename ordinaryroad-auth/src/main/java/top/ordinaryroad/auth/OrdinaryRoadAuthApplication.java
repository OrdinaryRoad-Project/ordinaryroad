package top.ordinaryroad.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import top.ordinaryroad.commons.security.annotation.EnableOrFeignClients;

/**
 * 认证授权应用
 *
 * @author mjz
 * @date 2021/9/3
 */
@EnableOrFeignClients
@SpringBootApplication
public class OrdinaryRoadAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrdinaryRoadAuthApplication.class, args);
    }
}
