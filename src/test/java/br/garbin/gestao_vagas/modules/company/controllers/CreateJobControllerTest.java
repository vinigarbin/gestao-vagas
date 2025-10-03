package br.garbin.gestao_vagas.modules.company.controllers;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.garbin.gestao_vagas.modules.company.dto.CreateJobDTO;
import br.garbin.gestao_vagas.modules.company.entities.CompanyEntity;
import br.garbin.gestao_vagas.modules.company.repositories.CompanyRepository;
import br.garbin.gestao_vagas.modules.utils.TestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CreateJobControllerTest {

  private MockMvc mvc;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Autowired
  private CompanyRepository companyRepository;

  @Before
  public void setUp() {
    mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
        .apply(SecurityMockMvcConfigurers.springSecurity())
        .build();
  }

  @Test
  public void shouldBeAbleToCreateANewJob() throws Exception {
    var company = CompanyEntity.builder().description("COMPANY TESTE")
        .email("company@test.com")
        .name("Company Test")
        .username("company_test")
        .password("1234567890")
        .website("www.companytest.com")
        .build();

    this.companyRepository.saveAndFlush(company);

    var jobDTO = CreateJobDTO.builder()
        .description("Desenvolvedor Java Pleno")
        .benefits("Vale transporte, Vale refeição")
        .level("PLENO")
        .build();

    mvc.perform(MockMvcRequestBuilders.post("/companies/jobs/")
        .contentType("application/json")
        .content(TestUtils.objectToJson(jobDTO))
        .header("Authorization",
            "Bearer " + TestUtils.generateToken(company.getId(), "MinhaChaveSecretaParaGerarOToken12345")))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  public void shouldNotBeAbleToCreateANewJobWhenCompanyDoesNotExist() throws Exception {
    var jobDTO = CreateJobDTO.builder()
        .description("Desenvolvedor Java Pleno")
        .benefits("Vale transporte, Vale refeição")
        .level("PLENO")
        .build();

    mvc.perform(MockMvcRequestBuilders.post("/companies/jobs/")
        .contentType("application/json")
        .content(TestUtils.objectToJson(jobDTO))
        .header("Authorization",
            "Bearer " + TestUtils.generateToken(UUID.randomUUID(), "MinhaChaveSecretaParaGerarOToken12345")))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }
}