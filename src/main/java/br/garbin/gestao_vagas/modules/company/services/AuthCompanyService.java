package br.garbin.gestao_vagas.modules.company.services;

import java.lang.reflect.Array;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.garbin.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import br.garbin.gestao_vagas.modules.company.dto.AuthCompanyResponseDTO;
import br.garbin.gestao_vagas.modules.company.repositories.CompanyRepository;

@Service
public class AuthCompanyService {

  @Value("${security.jwt.secret}")
  private String secretKey;

  @Autowired
  private CompanyRepository companyRepository;

  @Autowired
  private PasswordEncoder passwordEncoderService;

  public AuthCompanyResponseDTO execute(AuthCompanyDTO authCompany) throws AuthenticationException {
    var company = this.companyRepository.findByUsername(authCompany.getUsername()).orElseThrow(() -> {
      throw new UsernameNotFoundException("Username/password incorrect");
    });

    var passwordMatches = this.passwordEncoderService.matches(authCompany.getPassword(), company.getPassword());

    if (!passwordMatches) {
      throw new AuthenticationException();
    }

    var expires_in = Instant.now().plus(Duration.ofHours(2));
    Algorithm algorithm = Algorithm.HMAC256(secretKey);
    var token = JWT.create().withIssuer("javagas")
        .withExpiresAt(expires_in)
        .withSubject(company.getId().toString())
        .withClaim("roles", Arrays.asList("COMPANY")).sign(algorithm);

    return AuthCompanyResponseDTO.builder().access_token(token).expires_in(expires_in.toEpochMilli()).build();

  }
}
