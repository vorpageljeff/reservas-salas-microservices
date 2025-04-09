package com.ambientese.grupo5.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ambientese.grupo5.Model.FuncionarioModel;

public interface FuncionarioRepository extends JpaRepository<FuncionarioModel, Long>{
    List<FuncionarioModel> findFirst10ByNomeContainingIgnoreCaseOrderByNomeAsc(String nome);

    List<FuncionarioModel> findFirst10ByIdOrderByIdAsc(Long id);

}
