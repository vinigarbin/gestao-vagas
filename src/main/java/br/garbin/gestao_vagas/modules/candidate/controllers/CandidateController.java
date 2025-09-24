package br.garbin.gestao_vagas.modules.candidate.controllers;

import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.Array;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.garbin.gestao_vagas.modules.candidate.CandidateEntity;
import br.garbin.gestao_vagas.modules.candidate.services.CreateCandidateUseCase;
import br.garbin.gestao_vagas.modules.candidate.services.ListAllJobsByFilterUseCase;
import br.garbin.gestao_vagas.modules.candidate.services.ProfileCandidateUseCase;
import br.garbin.gestao_vagas.modules.company.entities.JobEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/candidates")
public class CandidateController {

  @Autowired
  private CreateCandidateUseCase createCandidateUseCase;

  @Autowired
  private ProfileCandidateUseCase candidateUseCase;

  @Autowired
  private ListAllJobsByFilterUseCase listAllJobsByFilterUseCase;

  @PostMapping()
  ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidate) {
    try {
      var result = this.createCandidateUseCase.execute(candidate);

      return ResponseEntity.status(201).body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping("/")
  @PreAuthorize("hasRole('CANDIDATE')")
  ResponseEntity<Object> get(HttpServletRequest request) {
    var id = request.getAttribute("candidateId");

    try {
      var result = this.candidateUseCase.execute(UUID.fromString(id.toString()));

      return ResponseEntity.status(201).body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @Tag(name = "Candidatos", description = "Informações do candidato")
  @Operation(summary = "Listagem de vagas disponivel para o candidato", description = "Essa função é responsavel por listar todas as vagas disponiveis baseadas no filtro")
  @ApiResponses({ @ApiResponse(responseCode = "200", content = {
      @Content(array = @ArraySchema(schema = @Schema(implementation = JobEntity.class))) })
  })
  @GetMapping("/jobs")
  @PreAuthorize("hasRole('CANDIDATE')")
  public List<JobEntity> getJob(@RequestParam String filter) {
    return this.listAllJobsByFilterUseCase.execute(filter);
  }
}
