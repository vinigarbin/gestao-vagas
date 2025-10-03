package br.garbin.gestao_vagas.modules.candidate.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.garbin.gestao_vagas.exceptions.UserNotFoundException;
import br.garbin.gestao_vagas.modules.candidate.CandidateRepository;
import br.garbin.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;

@Service
public class ProfileCandidateUseCase {

  @Autowired
  private CandidateRepository repository;

  public ProfileCandidateResponseDTO execute(UUID idCandidate) {
    var candidate = this.repository.findById(idCandidate)
        .orElseThrow(() -> {
          throw new UserNotFoundException();
        });

    return ProfileCandidateResponseDTO.builder().description(candidate.getDescription()).email(candidate.getEmail())
        .id(idCandidate).name(candidate.getName()).username(candidate.getUsername()).build();
  }
}
