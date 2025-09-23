package br.garbin.gestao_vagas.modules.candidate.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.garbin.gestao_vagas.exceptions.UserFoundException;
import br.garbin.gestao_vagas.modules.candidate.CandidateEntity;
import br.garbin.gestao_vagas.modules.candidate.CandidateRepository;

@Service
public class CreateCandidateUseCase {

  @Autowired
  private CandidateRepository candidateRepository;

  @Autowired
  PasswordEncoder passwordEncoderService;

  public CandidateEntity execute(CandidateEntity candidate) {
    this.candidateRepository.findByUsernameOrEmail(candidate.getUsername(), candidate.getEmail())
        .ifPresent((user) -> {
          throw new UserFoundException();
        });

    var password = candidate.getPassword();
    var encodedPassword = this.passwordEncoderService.encode(password);
    candidate.setPassword(encodedPassword);

    return this.candidateRepository.save(candidate);
  }
}
