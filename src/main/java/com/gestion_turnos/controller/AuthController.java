package com.gestion_turnos.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestion_turnos.dto.AuthResponseDTO;
import com.gestion_turnos.dto.LoginRequestDTO;
import com.gestion_turnos.dto.RegisterRequestDTO;
import com.gestion_turnos.model.Usuario;
import com.gestion_turnos.security.JwtUtils;
import com.gestion_turnos.service.AuthService;
import com.gestion_turnos.store.UsuarioStore;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UsuarioStore usuarioStore;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;
    
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequestDTO request) {

        if (usuarioStore.existeUsername(request.getUsername())) {
            return ResponseEntity.status(409).body("El username ya está en uso");
        }

        if (usuarioStore.existeEmail(request.getEmail())) {
            return ResponseEntity.status(409).body("El email ya está registrado");
        }

        authService.registrar(request);
        return ResponseEntity.status(201).body("Usuario registrado correctamente");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        Usuario usuario = (Usuario) auth.getPrincipal();

        List<String> roles = new ArrayList<>();
        for (GrantedAuthority authority : usuario.getAuthorities()) {
            roles.add(authority.getAuthority());
        }

        String token = jwtUtils.generarToken(usuario.getUsername(), roles);
        AuthResponseDTO response = new AuthResponseDTO(token, 3600, usuario.getUsername());
        return ResponseEntity.ok(response);
    }
}
