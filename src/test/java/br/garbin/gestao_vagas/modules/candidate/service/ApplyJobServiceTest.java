package br.garbin.gestao_vagas.modules.candidate.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.garbin.gestao_vagas.exceptions.UserNotFoundException;
import br.garbin.gestao_vagas.modules.candidate.CandidateRepository;
import br.garbin.gestao_vagas.modules.candidate.services.ApplyJobService;
import br.garbin.gestao_vagas.modules.company.repositories.JobRepository;

@ExtendWith(MockitoExtension.class)
public class ApplyJobServiceTest {

  @InjectMocks
  private ApplyJobService applyJobService;

  @Mock
  private CandidateRepository candidateRepository;

  @Mock
  private JobRepository jobRepository;

  @Test
  @DisplayName("Should not be able to apply job with candidate not found")
  public void shouldNotBeAbleToApplyJobWithCandidateNotFound() {
    try {
      applyJobService.execute(null, null);
    } catch (Exception e) {
      assertThat(e).isInstanceOf(UserNotFoundException.class);
    }
  }
}
