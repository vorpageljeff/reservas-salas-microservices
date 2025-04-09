package com.ambientese.grupo5.Controller.FormularioController;

import com.ambientese.grupo5.DTO.FormularioRequest;
import com.ambientese.grupo5.Model.FormularioModel;
import com.ambientese.grupo5.Model.PerguntasModel;
import com.ambientese.grupo5.Services.FormulariosService.BuscarPerguntasDoBancoService;
import com.ambientese.grupo5.Services.FormulariosService.ProcessarFormularioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class QuestionarioController {

    private final BuscarPerguntasDoBancoService buscarPerguntasService;
    private final ProcessarFormularioService processarFormularioService;

    @Autowired
    public QuestionarioController(BuscarPerguntasDoBancoService buscarPerguntasService, ProcessarFormularioService processarFormularioService) {
        this.buscarPerguntasService = buscarPerguntasService;
        this.processarFormularioService = processarFormularioService;
    }

    @GetMapping("/questionario")
    @ResponseBody
    public List<PerguntasModel> exibirQuestionario() {
        return buscarPerguntasService.buscarPerguntasDoBanco();
    }

    @PostMapping("/processarRespostas")
    @ResponseBody
    public FormularioModel processarRespostas(@RequestParam("empresa_id") Long empresa_id, @RequestBody List<FormularioRequest> respostas) {
        return processarFormularioService.criarProcessarEGerarCertificado(empresa_id, respostas);
    }

}
