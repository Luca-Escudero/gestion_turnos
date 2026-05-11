package com.gestion_turnos.store;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.gestion_turnos.model.Usuario;

@Component
public class UsuarioStore {

    private List<Usuario> usuarios = new ArrayList<>();

    private long contadorId = 1;

    private UsuarioStore(PasswordEncoder encoder) {

        List<String> rolesAdmin = new ArrayList<>();
        rolesAdmin.add("ROLE_ADMIN");
        Usuario admin = new Usuario(contadorId++, "admin", encoder.encode("admin123"), "admin@turnos.com", rolesAdmin);
        usuarios.add(admin);

        List<String> rolesMedicos = new ArrayList<>();
        rolesMedicos.add("ROLE_MEDICO");
        Usuario medico = new Usuario(contadorId++, "dra_garcia", encoder.encode("medico123"), "medico@turnos.com", rolesMedicos);
        usuarios.add(medico);
    }

    public void guardar(Usuario usuario) {
        usuario.setId(contadorId++);
        usuarios.add(usuario);
    }

    public Usuario buscarPorUsername(String username) {
            for (Usuario u : usuarios) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }

    public boolean existeUsername(String username) {
        for (Usuario u : usuarios) {
            if (u.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public boolean existeEmail(String email) { 
        for (Usuario u : usuarios) {
            if (u.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public List<Usuario> obtenerTodos() {
        return usuarios;
    }



    








    

}
