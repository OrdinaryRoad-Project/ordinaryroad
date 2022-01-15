package tech.ordinaryroad.auth.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.util.StopWatch;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 启动：Sa-OAuth2 Server端
 */
@Slf4j
@EnableFeignClients({"tech.ordinaryroad.**.**.api"})
@MapperScan({"tech.ordinaryroad.auth.server.dao"})
@SpringBootApplication
public class OrdinaryRoadAuthServerApp {

    public static void main(String[] args) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("run");
        SpringApplication.run(OrdinaryRoadAuthServerApp.class, args);
        stopWatch.stop();
        log.info("run end！ {}", stopWatch.prettyPrint());
    }

}
