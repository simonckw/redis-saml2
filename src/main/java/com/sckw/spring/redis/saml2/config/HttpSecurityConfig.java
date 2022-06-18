package com.sckw.spring.redis.saml2.config;

import org.springframework.boot.autoconfigure.session.DefaultCookieSerializerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class HttpSecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http, Saml2LoginSettings saml2LoginSettings)
      throws Exception {

    http.authorizeHttpRequests((authz) -> authz.anyRequest().hasRole("ADMIN"))
        .saml2Login(saml2LoginSettings);

    return http.build();
  }

  @Bean
  public DefaultCookieSerializerCustomizer cookieSerializerCustomizer() {
    return cookieSerializer -> {
      // Default of sameSite = "Lax" breaks SAML; setting to None with secure cookies here
      cookieSerializer.setSameSite("None");
      cookieSerializer.setUseSecureCookie(true);
    };
  }

}
