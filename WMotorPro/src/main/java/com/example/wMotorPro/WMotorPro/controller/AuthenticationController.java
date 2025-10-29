package com.example.wMotorPro.WMotorPro.controller;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
// IMPORTACIÓN CORREGIDA: Usamos el Jwt de Spring Security para el objeto decodificado.
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.RestController;

import com.example.wMotorPro.WMotorPro.modelo.dto.AuthenticationRequest;
import com.example.wMotorPro.WMotorPro.modelo.dto.AuthenticationResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
// Usaremos JwtException de Spring Security en lugar del de io.jsonwebtoken
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("auth")
public class AuthenticationController {
    
    @Autowired
    private JwtEncoder jwtEncoder;

    @Autowired
    private JwtDecoder jwtDecoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/token")
    public ResponseEntity<Map<String, String>> generaToken(String granType, UserDetails usuario, 
    Boolean whitRefreahToken, String refreahToken)
    {
        String subject = null;
        String scope = null;

        if(granType.equals("contrasenia")) {
            //Se auto indentifica al usuario con sus datos contrasenia
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuario.getUsername(), usuario.getPassword())
            );
            //se obtiene los datos del usuario 
            subject = authentication.getName();
            scope = authentication.getAuthorities()
            .stream().map(aut -> aut.getAuthority())
            .collect(Collectors.joining(" "));
        }
        else if (granType.equals("refreshToken")) {
            if (refreahToken == null){
                return new ResponseEntity<>(Map.of("errorMessage", "refresh token es requerido"), HttpStatus.UNAUTHORIZED);
            }
            Jwt decodeJWT = null;
            try {
                decodeJWT = jwtDecoder.decode(refreahToken);
            } catch (JwtException exception) {
                return new ResponseEntity<>(Map.of("errorMessage", exception.getMessage()), HttpStatus.UNAUTHORIZED);
            }

            subject = decodeJWT.getSubject();
            UserDetails usserDetail = userDetailsService.loadUserByUsername(subject);
            Collection <? extends GrantedAuthority> autorities = usserDetail.getAuthorities();
            scope = autorities.stream().map(auth -> auth.getAuthority()).collect(Collectors.joining(" "));        }
        
            Map<String,String> idToken = new HashMap<>();
            Instant instant = Instant.now();
            JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
            . subject(subject)
            .issuedAt(instant)
            .expiresAt(instant.plus(whitRefreahToken?1:5,ChronoUnit.MINUTES))
            .issuer("WMotorPro")
            .claim("scope", scope)
            .build();


            String jwtAccesToken = jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
            idToken.put("acecessToken", jwtAccesToken);

            if (whitRefreahToken) {
                JwtClaimsSet jwtClaimsSetRefreah = JwtClaimsSet.builder()
            . subject(subject)
            .issuedAt(instant)
            .expiresAt(instant.plus(whitRefreahToken?1:5,ChronoUnit.MINUTES))
            .issuer("WMotorPro")
            .claim("scope", scope)
            .build();

            String jwtRefreahToken = jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSetRefreah)).getTokenValue();
            idToken.put("acecessToken", jwtRefreahToken);
            }

            return new ResponseEntity<>(idToken, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authentication(@RequestBody AuthenticationRequest authenticationRequest) {    
        AuthenticationResponse response = a
    }

}
