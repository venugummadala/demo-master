package uk.co.fleabay.demo;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;

@SpringBootApplication(scanBasePackages = {"uk.co.fleabay.demo.*"})
public class FleabayApplication {

    public static void main(String[] args) {
        SpringApplication.run(FleabayApplication.class, args);
    }

}
