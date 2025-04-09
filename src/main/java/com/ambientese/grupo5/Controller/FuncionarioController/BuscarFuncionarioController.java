package com.ambientese.grupo5.Controller.FuncionarioController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ambientese.grupo5.Model.FuncionarioModel;
import com.ambientese.grupo5.Repository.FuncionarioRepository;


@RestController
@RequestMapping("/Funcionario")
@Validated
public class BuscarFuncionarioController {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @GetMapping("/search")
    public ResponseEntity<List<FuncionarioModel>> buscarEmpresas(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) Long id,
            @RequestParam(defaultValue = "0") int page) {
        List<FuncionarioModel> funcionarios;
        if (nome != null && !nome.isEmpty()) {
            funcionarios = funcionarioRepository.findFirst10ByNomeContainingIgnoreCaseOrderByNomeAsc(nome);
        } else if (id != null) {
            funcionarios = funcionarioRepository.findFirst10ByIdOrderByIdAsc(id);
        } else {
            Page<FuncionarioModel> empresasPage = funcionarioRepository.findAll(PageRequest.of(page, 25));
            funcionarios = empresasPage.getContent();
        }

        return ResponseEntity.ok(funcionarios);
    }
}
