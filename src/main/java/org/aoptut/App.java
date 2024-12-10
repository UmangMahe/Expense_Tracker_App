package org.aoptut;

import org.aoptut.auth.SecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@SpringBootApplication(scanBasePackages = {"org.aoptut"})
@EnableJpaRepositories(basePackages = {"org.aoptut.repository"})
@ComponentScan(basePackages = {"org.aoptut.controller", "org.aoptut.auth", "org.aoptut.service"})
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}