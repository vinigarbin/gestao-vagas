package br.garbin.gestao_vagas.modules.company.controllers;

import java.util.UUID;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.garbin.gestao_vagas.modules.company.dto.CreateJobDTO;
import br.garbin.gestao_vagas.modules.company.entities.JobEntity;
import br.garbin.gestao_vagas.modules.company.services.CreateJobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/companies/jobs")
public class JobController {

  @Autowired
  private CreateJobService createJobService;

  @PostMapping("/")
  @PreAuthorize("hasRole('COMPANY')")
  @Tag(name = "Vagas", description = "Informações do vagas")
  @Operation(summary = "Cadastro de vagas", description = "Essa função é responsavel por cadastrar uma nova vaga")
  @ApiResponses({ @ApiResponse(responseCode = "200", content = {
      @Content(schema = @Schema(implementation = JobEntity.class)) })
  })
  @GetMapping("/jobs")
  @SecurityRequirement(name = "jwt_auth")
  public ResponseEntity<Object> create(@Valid @RequestBody CreateJobDTO job, HttpServletRequest request) {
    var companyId = request.getAttribute("companyId");

    try {
      var jobEntity = JobEntity.builder()
          .description(job.getDescription())
          .level(job.getLevel())
          .benefits(job.getBenefits())
          .companyId(UUID.fromString(companyId.toString()))
          .build();

      var result = this.createJobService.execute(jobEntity);

      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }

  }
}
