package com.ambientese.grupo5.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ambientese.grupo5.Model.EmpresaModel;
import java.util.List;
import java.util.Optional;

public interface EmpresaRepository extends JpaRepository<EmpresaModel, Long> {

    @Query("SELECT DISTINCT e.ramo FROM EmpresaModel e")
    List<String> findDistinctRamos();

    List<EmpresaModel> findFirst10ByNomeFantasiaContainingIgnoreCaseOrderByNomeFantasiaAsc(String nomeFantasia);

    List<EmpresaModel> findFirst10ByRazaoSocialContainingIgnoreCaseOrderByRazaoSocialAsc(String razaoSocial);

    Optional<EmpresaModel> findByCnpj(String cnpj);
}
