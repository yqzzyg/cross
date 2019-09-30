package com.qfedu.cross;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class CrossApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrossApplication.class, args);
    }

}
