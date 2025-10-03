package br.garbin.gestao_vagas.modules.utils;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.UUID;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class TestUtils {
  public static String objectToJson(Object object) {
    try {
      var mapper = new com.fasterxml.jackson.databind.ObjectMapper();

      return mapper.writeValueAsString(object);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static String generateToken(UUID companyId, String secretKey) {
    var expires_in = Instant.now().plus(Duration.ofHours(2));
    Algorithm algorithm = Algorithm.HMAC256(secretKey);

    return JWT.create().withIssuer("javagas")
        .withExpiresAt(expires_in)
        .withSubject(companyId.toString())
        .withClaim("roles", Arrays.asList("COMPANY")).sign(algorithm);
  }

}
