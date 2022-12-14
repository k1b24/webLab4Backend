package com.kib.weblab4;

import com.kib.weblab4.security.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(RsaKeyProperties.class)
@SpringBootApplication
public class WebLab4Application {

    public static void main(String[] args) {
        SpringApplication.run(WebLab4Application.class, args);
    }

}
