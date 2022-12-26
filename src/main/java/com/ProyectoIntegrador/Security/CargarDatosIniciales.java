package com.ProyectoIntegrador.Security;

import com.ProyectoIntegrador.entity.Usuario;
import com.ProyectoIntegrador.entity.UsuarioRole;
import com.ProyectoIntegrador.repository.UsuarioRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CargarDatosIniciales implements ApplicationRunner {
    private UsuarioRepository usuarioRepository;

    public CargarDatosIniciales(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String passCifrada = bCryptPasswordEncoder.encode("1234");
        Usuario usuario = new Usuario("Gian","gianggg","gianUsuarioNormal@gmail.com",passCifrada, UsuarioRole.ROLE_USER);
        Usuario usuario2 = new Usuario("Gianp","giangggp","gianUsuarioAdmin@gmail.com",passCifrada, UsuarioRole.ROLE_ADMIN);
        usuarioRepository.save(usuario);
        usuarioRepository.save(usuario2);
    }
}
