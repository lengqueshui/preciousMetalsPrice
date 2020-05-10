package com.bsyd.preciousMetalsPrice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

//@ComponentScan(basePackages = "com.bsyd.preciousMetalsPrice")
@SpringBootApplication
@EnableScheduling
public class MainApplication {

    // 上下文信息
    public static ConfigurableApplicationContext context;

    public static void main(String[] args) throws Exception {
        context = SpringApplication.run(MainApplication.class, args);
    }
}
