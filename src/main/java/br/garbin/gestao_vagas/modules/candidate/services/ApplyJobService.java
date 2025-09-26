package br.garbin.gestao_vagas.modules.candidate.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.garbin.gestao_vagas.exceptions.JobNotFoundException;
import br.garbin.gestao_vagas.exceptions.UserNotFoundException;
import br.garbin.gestao_vagas.modules.candidate.CandidateRepository;
import br.garbin.gestao_vagas.modules.company.repositories.JobRepository;

@Service
public class ApplyJobService {

  @Autowired
  private CandidateRepository candidateRepository;

  @Autowired
  private JobRepository jobRepository;

  public void execute(UUID idCandidate, UUID idJob) {
    this.candidateRepository.findById(idCandidate).orElseThrow(() -> {
      throw new UserNotFoundException();
    });

    this.jobRepository.findById(idJob).orElseThrow(() -> {
      throw new JobNotFoundException();
    });
  }
}
