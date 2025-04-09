package com.ambientese.grupo5.Services.PerguntasService;

import com.ambientese.grupo5.Model.Enums.EixoEnum;
import com.ambientese.grupo5.Model.PerguntasModel;
import com.ambientese.grupo5.Repository.PerguntasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListarPerguntasPorEixoService {

    private final PerguntasRepository perguntasRepository;

    @Autowired
    public ListarPerguntasPorEixoService(PerguntasRepository perguntasRepository) {
        this.perguntasRepository = perguntasRepository;
    }

    public List<PerguntasModel> listarPerguntasPorEixo(EixoEnum eixo) {
        return perguntasRepository.findByEixo(eixo);
    }
}
