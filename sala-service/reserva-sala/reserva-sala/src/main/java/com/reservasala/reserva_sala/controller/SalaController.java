package com.reservasala.reserva_sala.controller;

import com.reservasala.reserva_sala.model.Sala;
import com.reservasala.reserva_sala.service.SalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/salas")
public class SalaController {
    @Autowired
    private SalaService service;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("salas", service.listar());
        return "salas";
    }

    @PostMapping("/salvar")
    public String salvar(Sala sala) {
        service.salvar(sala);
        return "redirect:/salas";
    }
}