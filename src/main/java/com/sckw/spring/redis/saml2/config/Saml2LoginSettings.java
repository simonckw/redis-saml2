package com.sckw.spring.redis.saml2.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.saml2.Saml2LoginConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.saml2.provider.service.authentication.Saml2AuthenticatedPrincipal;
import org.springframework.security.saml2.provider.service.authentication.Saml2Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
class Saml2LoginSettings implements Customizer<Saml2LoginConfigurer<HttpSecurity>> {

  @Override
  public void customize(Saml2LoginConfigurer<HttpSecurity> saml2LoginConfigurer) {

    saml2LoginConfigurer.successHandler(new SavedRequestAwareAuthenticationSuccessHandler() {

      @Override
      public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
          Authentication auth) throws IOException, ServletException {

        var saml2Auth = (Saml2Authentication) auth;
        var principal = (Saml2AuthenticatedPrincipal) saml2Auth.getPrincipal();
        var authorities = new ArrayList<GrantedAuthority>(saml2Auth.getAuthorities());

        assignAuthorities(authorities, principal);

        var updatedAuth =
            new Saml2Authentication(principal, saml2Auth.getSaml2Response(), authorities);

        SecurityContextHolder.getContext().setAuthentication(updatedAuth);

        super.onAuthenticationSuccess(request, response, updatedAuth);
      }

    });
  }

  private void assignAuthorities(List<GrantedAuthority> authorities,
      Saml2AuthenticatedPrincipal principal) {

    if (principal.getAttribute("role").contains("admin")) {
      authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

  }

}
