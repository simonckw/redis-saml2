package com.sckw.spring.redis.saml2.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class SecuredService {

  public String getData() {
    return "invoked successfully";
  }

}
