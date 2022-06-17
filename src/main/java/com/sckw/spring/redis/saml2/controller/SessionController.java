package com.sckw.spring.redis.saml2.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.saml2.provider.service.authentication.Saml2AuthenticatedPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SessionController {

  @RequestMapping("/")
  public String index() {
    return "home";
  }

  @RequestMapping("/secured/attribute")
  public String hello(@AuthenticationPrincipal Saml2AuthenticatedPrincipal principal,
      HttpServletRequest request, Model model) {
    model.addAttribute("rpRegistrationId", principal.getRelyingPartyRegistrationId());
    model.addAttribute("name", principal.getName());
    model.addAttribute("attributes", principal.getAttributes());

    return "attribute";
  }

}
