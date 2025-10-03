package br.garbin.gestao_vagas.modules.company.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.garbin.gestao_vagas.exceptions.CompanyNotFoundException;
import br.garbin.gestao_vagas.modules.company.entities.JobEntity;
import br.garbin.gestao_vagas.modules.company.repositories.CompanyRepository;
import br.garbin.gestao_vagas.modules.company.repositories.JobRepository;

@Service
public class CreateJobService {

  @Autowired
  private JobRepository jobRepository;

  @Autowired
  private CompanyRepository companyRepository;

  public JobEntity execute(JobEntity job) {
    companyRepository.findById(job.getCompanyId())
        .orElseThrow(() -> new CompanyNotFoundException());

    return this.jobRepository.save(job);
  }
}
