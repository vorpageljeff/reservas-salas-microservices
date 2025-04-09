package com.ambientese.grupo5.Services.EmpresaService;

import com.ambientese.grupo5.DTO.EmpresaRequest;
import com.ambientese.grupo5.Exception.ValidacaoException;
import com.ambientese.grupo5.Model.EmpresaModel;
import com.ambientese.grupo5.Repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AtualizarEmpresaService {

    private final EmpresaRepository empresaRepository;
    private final ValidacaoCamposObrigatoriosService validacaoCamposObrigatoriosService;
    private final ValidacaoCNPJService validacaoCNPJService;
    private final MapearEmpresaService mapearEmpresaService;

    @Autowired
    public AtualizarEmpresaService(EmpresaRepository empresaRepository, ValidacaoCamposObrigatoriosService validacaoCamposObrigatoriosService,
                                   ValidacaoCNPJService validacaoCNPJService, MapearEmpresaService mapearEmpresaService) {
        this.empresaRepository = empresaRepository;
        this.validacaoCamposObrigatoriosService = validacaoCamposObrigatoriosService;
        this.validacaoCNPJService = validacaoCNPJService;
        this.mapearEmpresaService = mapearEmpresaService;
    }

    public EmpresaModel atualizarEmpresa(Long id, EmpresaRequest empresaRequest) {
        EmpresaModel empresaModel = empresaRepository.findById(id)
                .orElseThrow(() -> new ValidacaoException("Empresa não encontrada com o ID: " + id));
        validacaoCamposObrigatoriosService.validarCamposObrigatorios(empresaRequest);
        validacaoCNPJService.validarCnpjUnico(empresaRequest.getCnpj(), id); // Passa o ID da empresa sendo atualizada
        mapearEmpresaService.mapearEmpresa(empresaModel, empresaRequest);
        return empresaRepository.save(empresaModel);
    }
}
