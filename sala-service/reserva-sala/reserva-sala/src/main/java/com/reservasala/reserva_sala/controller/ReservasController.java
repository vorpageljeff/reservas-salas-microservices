package com.reservasala.reserva_sala.controller;

import com.reservasala.reserva_sala.model.Reserva;
import com.reservasala.reserva_sala.model.Sala;
import com.reservasala.reserva_sala.model.Usuario;
import com.reservasala.reserva_sala.service.ReservaService;
import com.reservasala.reserva_sala.service.SalaService;
import com.reservasala.reserva_sala.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/reservas")
public class ReservasController {
    @Autowired
    private ReservaService reservaService;
    @Autowired
    private SalaService salaService;
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("reservas", reservaService.listar());
        model.addAttribute("salas", salaService.listar());
        model.addAttribute("usuarios", usuarioService.listar());
        return "reservas";
    }

    @PostMapping("/salvar")
    public String salvar(@RequestParam Long salaId, @RequestParam Long usuarioId, @RequestParam String dataHora) {
        Sala sala = salaService.listar().stream().filter(s -> s.getId().equals(salaId)).findFirst().orElse(null);
        Usuario usuario = usuarioService.listar().stream().filter(u -> u.getId().equals(usuarioId)).findFirst().orElse(null);
        Reserva reserva = new Reserva(null, LocalDateTime.parse(dataHora), sala, usuario);
        reservaService.salvar(reserva);
        return "redirect:/reservas";
    }
}