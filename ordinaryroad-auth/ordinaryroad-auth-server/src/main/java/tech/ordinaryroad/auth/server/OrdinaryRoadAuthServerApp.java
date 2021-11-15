package tech.ordinaryroad.auth.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 启动：Sa-OAuth2 Server端
 */
@EnableFeignClients({"tech.ordinaryroad.upms.api"})
@MapperScan({"tech.ordinaryroad.auth.server.dao"})
@SpringBootApplication
public class OrdinaryRoadAuthServerApp {
    public static void main(String[] args) {
        SpringApplication.run(OrdinaryRoadAuthServerApp.class, args);
    }
}
