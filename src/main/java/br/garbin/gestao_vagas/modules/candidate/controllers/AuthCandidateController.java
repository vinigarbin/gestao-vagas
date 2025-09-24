package br.garbin.gestao_vagas.modules.candidate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.garbin.gestao_vagas.modules.candidate.dto.AuthCandidateRequestDTO;
import br.garbin.gestao_vagas.modules.candidate.services.AuthCandidateUseCase;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/candidates")
public class AuthCandidateController {

  @Autowired
  private AuthCandidateUseCase service;

  @PostMapping("/auth")
  public ResponseEntity<Object> auth(@RequestBody @Valid AuthCandidateRequestDTO dto) {
    try {
      var token = this.service.execute(dto);

      return ResponseEntity.ok().body(token);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
  }
}
