package br.garbin.gestao_vagas.modules.company.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.garbin.gestao_vagas.exceptions.UserFoundException;
import br.garbin.gestao_vagas.modules.company.entities.CompanyEntity;
import br.garbin.gestao_vagas.modules.company.repositories.CompanyRepository;

@Service
public class CreateCompanyService {

  @Autowired
  private CompanyRepository companyRepository;
  @Autowired
  private PasswordEncoder passwordEncoderService;

  public CompanyEntity execute(CompanyEntity company) {
    this.companyRepository.findByUsernameOrEmail(company.getUsername(), company.getEmail())
        .ifPresent((user) -> {
          throw new UserFoundException();
        });

    var password = company.getPassword();
    var encodedPassword = this.passwordEncoderService.encode(password);
    company.setPassword(encodedPassword);

    return this.companyRepository.save(company);
  }
}
