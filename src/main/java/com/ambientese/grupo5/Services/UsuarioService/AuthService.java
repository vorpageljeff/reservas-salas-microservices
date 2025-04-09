package com.ambientese.grupo5.Services.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ambientese.grupo5.Model.UsuarioModel;
import com.ambientese.grupo5.Repository.UsuarioRepository;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository userRepository;

    public boolean authenticate(String login, String password) {
        UsuarioModel user = userRepository.findByLogin(login);
        return user != null && user.getPassword().equals(password);
    }
}
