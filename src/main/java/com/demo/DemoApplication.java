package com.demo;

import com.demo.modal.config.SPDQprperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@MapperScan(basePackages = "com.demo.mapper")
@EnableConfigurationProperties(SPDQprperties.class)
public class DemoApplication {


//    @RequestMapping("/hello")
//    public String hello(){
//        return "hello";
//    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
