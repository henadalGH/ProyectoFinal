package com.example.wmotorproBack.wmotorBack.Security;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.wmotorproBack.wmotorBack.Servicio.JWTUtilityService;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    JWTUtilityService jwtUtilityService;

    public JWTAuthorizationFilter(JWTUtilityService jwtUtilityService){
        this.jwtUtilityService = jwtUtilityService;
    }

    @Override
protected void doFilterInternal(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain)
        throws ServletException, IOException {

    String header = request.getHeader("Authorization");

    if (header == null || !header.startsWith("Bearer ")) {
        filterChain.doFilter(request, response);
        return;
    }

    String token = header.substring(7);

    // Agregar manejo de excepciones específicas y mejorar el mensaje de error
    try {
        JWTClaimsSet claims = jwtUtilityService.parseJWT(token);

        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(
                claims.getSubject(),
                null,
                Collections.emptyList()
            );

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

    } catch (ParseException e) {
        throw new RuntimeException("Error al analizar el JWT", e);
    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
        throw new RuntimeException("Error en la configuración de la clave JWT", e);
    } catch (Exception e) {
        throw new RuntimeException("Error inesperado al validar el JWT", e);
    }

    filterChain.doFilter(request, response);
}
}