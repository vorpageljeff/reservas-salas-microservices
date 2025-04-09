package com.ambientese.grupo5.Repository;

import com.ambientese.grupo5.Model.FormularioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FormularioRepository extends JpaRepository<FormularioModel, Long>, JpaSpecificationExecutor<FormularioModel> {
    
    @Query("SELECT f FROM FormularioModel f WHERE f.id IN (SELECT MAX(f2.id) FROM FormularioModel f2 GROUP BY f2.empresa.id) ORDER BY f.pontuacaoFinal DESC")
    List<FormularioModel> findLatestByEmpresaOrderByPontuacaoFinalDesc();

    List<FormularioModel> findAllByOrderByPontuacaoFinalAsc();
    List<FormularioModel> findAllByOrderByPontuacaoSocialAsc();
    List<FormularioModel> findAllByOrderByPontuacaoAmbientalAsc();
    List<FormularioModel> findAllByOrderByPontuacaoGovernamentalAsc();

    List<FormularioModel> findByEmpresaId(Long empresaId);
}
