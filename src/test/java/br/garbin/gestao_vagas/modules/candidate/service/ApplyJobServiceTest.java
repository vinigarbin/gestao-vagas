package br.garbin.gestao_vagas.modules.candidate.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.garbin.gestao_vagas.exceptions.JobNotFoundException;
import br.garbin.gestao_vagas.exceptions.UserNotFoundException;
import br.garbin.gestao_vagas.modules.candidate.CandidateEntity;
import br.garbin.gestao_vagas.modules.candidate.CandidateRepository;
import br.garbin.gestao_vagas.modules.candidate.entities.ApplyJobEntity;
import br.garbin.gestao_vagas.modules.candidate.repositories.ApplyJobRepository;
import br.garbin.gestao_vagas.modules.candidate.services.ApplyJobService;
import br.garbin.gestao_vagas.modules.company.entities.JobEntity;
import br.garbin.gestao_vagas.modules.company.repositories.JobRepository;

@ExtendWith(MockitoExtension.class)
public class ApplyJobServiceTest {

  @InjectMocks
  private ApplyJobService applyJobService;

  @Mock
  private CandidateRepository candidateRepository;

  @Mock
  private JobRepository jobRepository;

  @Mock
  private ApplyJobRepository applyJobRepository;

  @Test
  @DisplayName("Should not be able to apply job with candidate not found")
  public void shouldNotBeAbleToApplyJobWithCandidateNotFound() {
    try {
      applyJobService.execute(null, null);
    } catch (Exception e) {
      assertThat(e).isInstanceOf(UserNotFoundException.class);
    }
  }

  @Test
  @DisplayName("Should not be able to apply job with job not found")
  public void shouldNotBeAbleToApplyJobWithJobNotFound() {
    var idCandidate = UUID.randomUUID();
    var candidate = new CandidateEntity();
    when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(candidate));

    try {
      applyJobService.execute(idCandidate, null);
    } catch (Exception e) {
      assertThat(e).isInstanceOf(JobNotFoundException.class);
    }
  }

  @Test
  @DisplayName("Should be able to create a new apply job")
  public void shouldBeAbleToCreateANewApplyJob() {
    var idCandidate = UUID.randomUUID();
    var idJob = UUID.randomUUID();

    var applyJob = ApplyJobEntity.builder()
        .candidateId(idCandidate)
        .jobId(idJob)
        .build();

    var applyJobCreated = ApplyJobEntity.builder().id(UUID.randomUUID()).build();

    when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(new CandidateEntity()));
    when(jobRepository.findById(idJob)).thenReturn(Optional.of(new JobEntity()));
    when(applyJobRepository.save(applyJob)).thenReturn(applyJobCreated);

    var result = applyJobService.execute(idCandidate, idJob);

    assertThat(result).hasFieldOrProperty("id");
    assertNotNull(result.getId());
  }
}
