package com.ambientese.grupo5.Controller.FormularioController;

import com.ambientese.grupo5.DTO.FormularioRanking;
import com.ambientese.grupo5.Model.FormularioModel;
import com.ambientese.grupo5.Model.Enums.PorteEnum;
import com.ambientese.grupo5.Services.FormulariosService.RankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/ranking")
public class RankingController {

    private final RankingService rankingService;

    @Autowired
    public RankingController(RankingService rankingService) {
        this.rankingService = rankingService;
    }

    @GetMapping("/pontuacao")
    public ResponseEntity<List<FormularioRanking>> classificarPorPontuacao(
            @RequestParam(required = false) String nomeFantasia,
            @RequestParam(required = false) String ramo,
            @RequestParam(required = false) PorteEnum porte,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        List<FormularioRanking> resultado = rankingService.classificarPorPontuacaoWithFilter(nomeFantasia, ramo, porte, page, size);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/ramos/list")
    public List<String> listarRamos() {
        return rankingService.listarRamos();
    }

    @GetMapping("/ambiental")
    public List<FormularioModel> classificarPorEixoAmbiental() {
        return rankingService.classificarPorEixoAmbiental();
    }

    @GetMapping("/social")
    public List<FormularioModel> classificarPorEixoSocial() {
        return rankingService.classificarPorEixoSocial();
    }

    @GetMapping("/governamental")
    public List<FormularioModel> classificarPorEixoGovernamental() {
        return rankingService.classificarPorEixoGovernamental();
    }
}
