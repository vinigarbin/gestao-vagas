package br.garbin.gestao_vagas.modules.company.services;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.garbin.gestao_vagas.modules.company.dtos.AuthCompanyDTO;
import br.garbin.gestao_vagas.modules.company.entities.CompanyEntity;
import br.garbin.gestao_vagas.modules.company.repositories.CompanyRepository;

@Service
public class AuthCompanyService {

  @Value("${security.jwt.secret}")
  private String secretKey;

  @Autowired
  private CompanyRepository companyRepository;

  @Autowired
  private PasswordEncoder passwordEncoderService;

  public String execute(AuthCompanyDTO authCompany) throws AuthenticationException {
    var company = this.companyRepository.findByUsername(authCompany.getUsername()).orElseThrow(() -> {
      throw new UsernameNotFoundException("Username/password incorrect");
    });

    var passwordMatches = this.passwordEncoderService.matches(authCompany.getPassword(), company.getPassword());

    if (!passwordMatches) {
      throw new AuthenticationException();
    }

    Algorithm algorithm = Algorithm.HMAC256(secretKey);
    var token = JWT.create().withIssuer("javagas").withSubject(company.getId().toString()).sign(algorithm);

    return token;
  }
}
