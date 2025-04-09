package com.ambientese.grupo5.Controller.EmpresaController;

import com.ambientese.grupo5.Model.EmpresaModel;
import com.ambientese.grupo5.Repository.EmpresaRepository;
import com.ambientese.grupo5.Services.EmpresaService.ListarEmpresaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Empresa")
@Validated
public class BuscarEmpresaController {

    private final EmpresaRepository empresaRepository;
    private final ListarEmpresaService listarEmpresaService;

    public BuscarEmpresaController(EmpresaRepository empresaRepository, ListarEmpresaService listarEmpresaService)
    {
        this.empresaRepository = empresaRepository;
        this.listarEmpresaService = listarEmpresaService;

    }

    @GetMapping
    public ResponseEntity<List<EmpresaModel>> getAllEmpresas() {
        List<EmpresaModel> empresaModels = listarEmpresaService.getAllEmpresas();
        return ResponseEntity.ok(empresaModels);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarEmpresaPorId(@PathVariable Long id) {
        Optional<EmpresaModel> empresa = empresaRepository.findById(id);
        return empresa.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<EmpresaModel>> buscarEmpresas(
            @RequestParam(required = false) String nomeFantasia,
            @RequestParam(required = false) String razaoSocial,
            @RequestParam(defaultValue = "0") int page) {
        List<EmpresaModel> empresas;
        if (nomeFantasia != null && !nomeFantasia.isEmpty()) {
            empresas = empresaRepository.findFirst10ByNomeFantasiaContainingIgnoreCaseOrderByNomeFantasiaAsc(nomeFantasia);
        } else if (razaoSocial != null && !razaoSocial.isEmpty()) {
            empresas = empresaRepository.findFirst10ByRazaoSocialContainingIgnoreCaseOrderByRazaoSocialAsc(razaoSocial);
        } else {
            Page<EmpresaModel> empresasPage = empresaRepository.findAll(PageRequest.of(page, 25));
            empresas = empresasPage.getContent();
        }

        return ResponseEntity.ok(empresas);
    }

}
