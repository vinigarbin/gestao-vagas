package br.garbin.gestao_vagas.modules.company.services;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.garbin.gestao_vagas.modules.company.dtos.AuthCompanyDTO;
import br.garbin.gestao_vagas.modules.company.entities.CompanyEntity;
import br.garbin.gestao_vagas.modules.company.repositories.CompanyRepository;

@Service
public class AuthCompanyService {

  @Autowired
  private CompanyRepository companyRepository;

  @Autowired
  private PasswordEncoder passwordEncoderService;

  public CompanyEntity execute(AuthCompanyDTO authCompany) throws AuthenticationException {
    var company = this.companyRepository.findByUsername(authCompany.getUsername())
        .orElseThrow(() -> new UsernameNotFoundException("Company not found"));

    var passwordMatches = this.passwordEncoderService.matches(authCompany.getPassword(), company.getPassword());

    if (!passwordMatches) {
      throw new AuthenticationException();
    }

    return company;
  }
}
