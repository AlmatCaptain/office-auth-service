package kz.iitu.office.auth.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class OfficeAuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OfficeAuthServiceApplication.class, args);
    }

}
