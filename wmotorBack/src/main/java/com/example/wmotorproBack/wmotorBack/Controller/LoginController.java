package com.example.wmotorproBack.wmotorBack.Controller;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.LoginDTO;
import com.example.wmotorproBack.wmotorBack.Servicio.AuthService;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
public class LoginController {
    @Autowired
    private AuthService authService;

    
    @PostMapping("/login")
public ResponseEntity<HashMap<String, String>> login(@RequestBody LoginDTO loginRequest) throws Exception {

    HashMap<String, String> login = authService.login(loginRequest);

    if (login.containsKey("jwt")) {
        return ResponseEntity.ok(login);
    } else {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(login);
    }
}

@GetMapping("/admin/solo")
@PreAuthorize("hasRole('CLIENTE')")
public Map<String, Object> soloAdmin() {

    var auth = SecurityContextHolder.getContext().getAuthentication();

    Map<String, Object> data = new HashMap<>();
    data.put("usuario", auth.getName());
    data.put("roles", auth.getAuthorities());

    return data;
}


    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            return ResponseEntity.badRequest().body("Token no proporcionado");
        }
        String token = authHeader.substring(7);
        authService.logout(token);
        return ResponseEntity.ok("Seccion serrada con exito");
    }
    
}