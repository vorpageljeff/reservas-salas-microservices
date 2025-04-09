package com.ambientese.grupo5.Services.EmpresaService;

import com.ambientese.grupo5.Model.EmpresaModel;
import com.ambientese.grupo5.Model.FormularioModel;
import com.ambientese.grupo5.Repository.EmpresaRepository;
import com.ambientese.grupo5.Repository.FormularioRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeletarEmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private FormularioRepository formularioRepository;

    public void deleteEmpresa(Long id) {
        EmpresaModel empresa = empresaRepository.findById(id).orElse(null);
        if (empresa != null) {
            List<FormularioModel> formularios = formularioRepository.findByEmpresaId(empresa.getId());
            formularios.forEach(formularioRepository::delete);
            empresaRepository.delete(empresa);
        }
    }
}
