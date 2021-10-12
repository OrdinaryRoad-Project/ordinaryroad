package top.ordinaroroad.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author mjz
 * @date 2021/9/4
 */

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class OrdinaryRoadGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrdinaryRoadGatewayApplication.class, args);
    }

}
