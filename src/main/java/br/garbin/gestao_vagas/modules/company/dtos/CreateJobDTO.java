package br.garbin.gestao_vagas.modules.company.dtos;

import lombok.Data;

@Data
public class CreateJobDTO {
  private String description;
  private String benefits;
  private String level;
}
