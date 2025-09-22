package br.garbin.gestao_vagas.modules.company.controllers;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.garbin.gestao_vagas.modules.company.dtos.AuthCompanyDTO;
import br.garbin.gestao_vagas.modules.company.services.AuthCompanyService;

@RestController
@RequestMapping("/auth")
public class AuthCompanyController {

  @Autowired
  private AuthCompanyService authCompanyService;

  @PostMapping("/company")
  public String authenticate(@RequestBody AuthCompanyDTO authCompany) throws AuthenticationException {
    return this.authCompanyService.execute(authCompany);
  }
}
