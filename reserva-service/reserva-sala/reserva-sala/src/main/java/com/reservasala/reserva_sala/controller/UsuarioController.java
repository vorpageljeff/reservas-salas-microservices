package com.reservasala.reserva_sala.controller;

import com.reservasala.reserva_sala.model.Usuario;
import com.reservasala.reserva_sala.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService service;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("usuarios", service.listar());
        return "usuarios";
    }

    @PostMapping("/salvar")
    public String salvar(Usuario usuario) {
        service.salvar(usuario);
        return "redirect:/usuarios";
    }
}