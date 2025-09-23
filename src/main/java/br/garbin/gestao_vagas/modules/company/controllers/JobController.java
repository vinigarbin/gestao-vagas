package br.garbin.gestao_vagas.modules.company.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.garbin.gestao_vagas.modules.company.dtos.CreateJobDTO;
import br.garbin.gestao_vagas.modules.company.entities.JobEntity;
import br.garbin.gestao_vagas.modules.company.services.CreateJobService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/jobs")
public class JobController {

  @Autowired
  private CreateJobService createJobService;

  @PostMapping()
  public JobEntity create(@Valid @RequestBody CreateJobDTO job, HttpServletRequest request) {
    var companyId = request.getAttribute("companyId");
   
   var jobEntity =  JobEntity.builder()
        .description(job.getDescription())
        .level(job.getLevel())
        .benefits(job.getBenefits())
        .companyId(UUID.fromString(companyId.toString()))
        .build();

    return this.createJobService.execute(jobEntity);
  }
}
