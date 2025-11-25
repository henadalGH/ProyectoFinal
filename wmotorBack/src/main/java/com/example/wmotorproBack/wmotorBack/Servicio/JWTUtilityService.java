package com.example.wmotorproBack.wmotorBack.Servicio;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;

public interface JWTUtilityService {

    public String generateJWT(Long userId) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException, JOSEException;
    public JWTClaimsSet parseJWT(String jwt) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException, ParseException, JOSEException;
}
