package br.garbin.gestao_vagas.modules.candidate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.garbin.gestao_vagas.modules.candidate.CandidateEntity;
import br.garbin.gestao_vagas.modules.candidate.services.CreateCandidateUseCase;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/candidates")
public class CandidateController {

  @Autowired
  private CreateCandidateUseCase createCandidateUseCase;

  @PostMapping()
  ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidate) {
    try {
      var result = this.createCandidateUseCase.execute(candidate);

      return ResponseEntity.status(201).body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
