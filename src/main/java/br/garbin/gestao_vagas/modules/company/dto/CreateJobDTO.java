package br.garbin.gestao_vagas.modules.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateJobDTO {
  @Schema(example = "Desenvolvedor Java Pleno", requiredMode = RequiredMode.REQUIRED)
  private String description;
  @Schema(example = "Vale transporte, Vale refeição", requiredMode = RequiredMode.REQUIRED)
  private String benefits;
  @Schema(example = "PLENO", requiredMode = RequiredMode.REQUIRED)
  private String level;
}
