package com.sckw.spring.redis.saml2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }



}
