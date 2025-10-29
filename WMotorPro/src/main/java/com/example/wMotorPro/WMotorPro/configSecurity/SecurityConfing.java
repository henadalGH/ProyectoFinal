package com.example.wMotorPro.WMotorPro.configSecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.wMotorPro.WMotorPro.repository.UsuarioRepository;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfing {


    @Autowired
    private RsaKeysConfig rsaKeysConfig;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public AuthenticationManager authenticationManager() throws Exception
    {
        return authenticationConfiguration.getAuthenticationManager();
    }    

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService)
    {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetailsService());
        return new ProviderManager(provider);
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain( HttpSecurity httpSecurity) throws Exception
    {
        return httpSecurity
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth.requestMatchers("/token/**").permitAll())
            .authorizeHttpRequests((auth)->  auth.anyRequest().authenticated())
            .sessionManagement(ses-> ses.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            //.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
            .oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()))
            .httpBasic(Customizer.withDefaults())
            .build();
    }


    //Decodifica y valida  los token JWT entrantes ( usa una clave publica)
    @Bean
    JwtDecoder jwtDecoder()
    {
        return NimbusJwtDecoder.withPublicKey(rsaKeysConfig.publicKey()).build();
    }

    //genera y firma los token JWK salientes 
    @Bean
    JwtEncoder jwtEncoder()
    {
    JWK jwt = new RSAKey.Builder(rsaKeysConfig.publicKey()).privateKey(rsaKeysConfig.privateKey()).build();
    JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(jwt));
    return new NimbusJwtEncoder(jwkSource);
    }

    @Bean
    public UserDetailsService userDetailsService()
    {
        return (email) -> {
            return usuarioRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("El email ingresado no existe"));
        };
    }

}
