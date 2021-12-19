package com.jqcx;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo //开启Dubbo 的注解支持
public class Dt2ServerProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(Dt2ServerProviderApplication.class, args);
    }

}
