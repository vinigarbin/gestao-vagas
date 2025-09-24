package br.garbin.gestao_vagas.modules.company.controllers;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.garbin.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import br.garbin.gestao_vagas.modules.company.dto.AuthCompanyResponseDTO;
import br.garbin.gestao_vagas.modules.company.services.AuthCompanyService;

@RestController
@RequestMapping("/companies")
public class AuthCompanyController {

  @Autowired
  private AuthCompanyService authCompanyService;

  @PostMapping("/auth")
  public ResponseEntity<Object> authenticate(@RequestBody AuthCompanyDTO authCompany) throws AuthenticationException {

    try {
      var token = this.authCompanyService.execute(authCompany);

      return ResponseEntity.ok().body(token);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
  }
}
