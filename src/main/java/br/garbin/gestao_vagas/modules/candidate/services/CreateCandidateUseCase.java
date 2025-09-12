package br.garbin.gestao_vagas.modules.candidate.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.garbin.gestao_vagas.exceptions.UserFoundException;
import br.garbin.gestao_vagas.modules.candidate.CandidateEntity;
import br.garbin.gestao_vagas.modules.candidate.CandidateRepository;

@Service
public class CreateCandidateUseCase {

  @Autowired
  private CandidateRepository candidateRepository;

  public CandidateEntity execute(CandidateEntity candidate) {
    this.candidateRepository.findByUsernameOrEmail(candidate.getUsername(), candidate.getEmail())
        .ifPresent((user) -> {
          throw new UserFoundException();
        });

    return this.candidateRepository.save(candidate);
  }
}
