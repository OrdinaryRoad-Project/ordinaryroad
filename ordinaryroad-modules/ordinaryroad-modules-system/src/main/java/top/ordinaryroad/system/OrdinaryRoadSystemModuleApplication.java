package top.ordinaryroad.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import top.ordinaryroad.commons.swagger.annotation.EnableCustomSwagger2;

/**
 * @author mjz
 * @date 2021/9/4
 */
@SpringBootApplication
@EnableFeignClients
@EnableCustomSwagger2
@EnableJpaAuditing
public class OrdinaryRoadSystemModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrdinaryRoadSystemModuleApplication.class, args);
    }

}
