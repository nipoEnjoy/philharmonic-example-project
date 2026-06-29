package com.npopov.philharmonic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class PhilharmonicApplication {

    public static void main(String[] args) {
        SpringApplication.run(PhilharmonicApplication.class, args);
    }

}
