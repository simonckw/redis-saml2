package com.sckw.spring.redis.saml2.controller;

import com.sckw.spring.redis.saml2.service.SecuredService;
import java.security.Principal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.saml2.provider.service.authentication.Saml2AuthenticatedPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SessionController {

  private SecuredService securedService;

  public SessionController(SecuredService securedService) {
    this.securedService = securedService;
  }


  @RequestMapping("/")
  public String index() {
    return "home";
  }

  @RequestMapping("/secured/attribute")
  public String attribute(Principal principal, Authentication authentication, Model model) {

    // Saml2 specific data can be obtained if required

    var saml2Principal = (Saml2AuthenticatedPrincipal) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();

    // Spring-boot 2.6 or above
    model.addAttribute("rpRegistrationId", saml2Principal.getRelyingPartyRegistrationId());
    model.addAttribute("attributes", saml2Principal.getAttributes());


    // Name and authorities as generic Spring Security objects; should be good in most situations

    model.addAttribute("name", principal.getName());

    var authorities = authentication.getAuthorities();
    model.addAttribute("authorities", authorities);


    // Testing Spring Method Security
    model.addAttribute("Secured", securedService.getData());

    return "attribute";
  }

}
