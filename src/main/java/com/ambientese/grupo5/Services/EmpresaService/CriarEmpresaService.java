package com.ambientese.grupo5.Services.EmpresaService;

import com.ambientese.grupo5.DTO.EmpresaRequest;
import com.ambientese.grupo5.Model.EmpresaModel;
import com.ambientese.grupo5.Model.EnderecoModel;
import com.ambientese.grupo5.Repository.EmpresaRepository;
import com.ambientese.grupo5.Repository.EnderecoRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class CriarEmpresaService {

    private final EmpresaRepository empresaRepository;
    private final EnderecoRepository enderecoRepository;
    private final ValidacaoCamposObrigatoriosService validacaoCamposObrigatoriosService;
    private final ValidacaoCNPJService validacaoCNPJService;
    private final MapearEmpresaService mapearEmpresaService;
    private final MapearEnderecoService mapearEnderecoService;

    @Autowired
    public CriarEmpresaService(EmpresaRepository empresaRepository, EnderecoRepository enderecoRepository,
                               ValidacaoCamposObrigatoriosService validacaoCamposObrigatoriosService,
                               ValidacaoCNPJService validacaoCNPJService,
                               MapearEmpresaService mapearEmpresaService,
                               MapearEnderecoService mapearEnderecoService) {
        this.empresaRepository = empresaRepository;
        this.enderecoRepository = enderecoRepository;
        this.validacaoCamposObrigatoriosService = validacaoCamposObrigatoriosService;
        this.validacaoCNPJService = validacaoCNPJService;
        this.mapearEmpresaService = mapearEmpresaService;
        this.mapearEnderecoService = mapearEnderecoService;
    }

    public EmpresaModel criarEmpresa(EmpresaRequest empresaRequest) {
        validacaoCamposObrigatoriosService.validarCamposObrigatorios(empresaRequest);
        validacaoCNPJService.validarCnpjUnico(empresaRequest.getCnpj(), null);
        EnderecoModel enderecoModel = new EnderecoModel();
        mapearEnderecoService.mapearEndereco(enderecoModel, empresaRequest);
        EnderecoModel enderecoSalvo = enderecoRepository.save(enderecoModel);
        EmpresaModel empresaModel = new EmpresaModel();
        mapearEmpresaService.mapearEmpresa(empresaModel, empresaRequest);
        empresaModel.setEndereco(enderecoSalvo);
        return empresaRepository.save(empresaModel);
    }
}
