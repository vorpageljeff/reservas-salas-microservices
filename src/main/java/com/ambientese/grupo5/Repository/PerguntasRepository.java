package com.ambientese.grupo5.Repository;

import com.ambientese.grupo5.Model.Enums.EixoEnum;
import com.ambientese.grupo5.Model.PerguntasModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PerguntasRepository extends JpaRepository<PerguntasModel, Long> {
    List<PerguntasModel> findByEixo(EixoEnum eixo);

}
