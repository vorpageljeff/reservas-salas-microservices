package com.ambientese.grupo5.Services.PerguntasService;

import com.ambientese.grupo5.Model.Enums.EixoEnum;
import com.ambientese.grupo5.Model.PerguntasModel;
import com.ambientese.grupo5.Repository.PerguntasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CriarPerguntasService {

    private final PerguntasRepository perguntasRepository;

    @Autowired
    public CriarPerguntasService(PerguntasRepository perguntasRepository) {
        this.perguntasRepository = perguntasRepository;
    }

    public PerguntasModel criarPergunta(String descricao, EixoEnum eixo) {
        PerguntasModel novaPergunta = new PerguntasModel();
        novaPergunta.setDescricao(descricao);
        novaPergunta.setEixo(eixo);
        return perguntasRepository.save(novaPergunta);
    }
}
