package com.liberhome.liber_center;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.liberhome.liber_center.mapper")
public class LiberCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(LiberCenterApplication.class, args);
    }

}
