package com.ambientese.grupo5.Controller.PerguntasController;

import com.ambientese.grupo5.Services.PerguntasService.DeletarPerguntasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Perguntas")
public class DeletarPerguntasController {

    private final DeletarPerguntasService deletarPerguntasService;

    @Autowired
    public DeletarPerguntasController(DeletarPerguntasService deletarPerguntasService) {
        this.deletarPerguntasService = deletarPerguntasService;
    }

    @DeleteMapping("/Delete/{id}")
    public ResponseEntity<String> deletarPergunta(@PathVariable long id) {
        deletarPerguntasService.deletarPergunta(id);
        return ResponseEntity.noContent().build();
    }
}
