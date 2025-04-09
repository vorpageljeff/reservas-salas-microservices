package com.ambientese.grupo5.Services.FuncionarioService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ambientese.grupo5.Model.FuncionarioModel;
import com.ambientese.grupo5.Repository.FuncionarioRepository;


@Service
public class DeletarFuncionarioService {
    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public void deleteFuncionario(Long id) {
        FuncionarioModel empresa = funcionarioRepository.findById(id).orElse(null);
        funcionarioRepository.delete(empresa);
    }
}
