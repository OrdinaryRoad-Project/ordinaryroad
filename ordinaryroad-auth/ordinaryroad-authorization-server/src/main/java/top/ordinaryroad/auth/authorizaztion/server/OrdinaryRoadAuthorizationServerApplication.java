package top.ordinaryroad.auth.authorizaztion.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 授权应用
 *
 * @author mjz
 * @date 2021/9/3
 */
@EnableFeignClients(basePackages = "top.ordinaryroad.system")
@SpringBootApplication
public class OrdinaryRoadAuthorizationServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrdinaryRoadAuthorizationServerApplication.class, args);
    }
}
