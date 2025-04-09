package com.ambientese.grupo5.Services.FormulariosService;

import com.ambientese.grupo5.Model.Enums.EixoEnum;
import com.ambientese.grupo5.Model.PerguntasModel;
import com.ambientese.grupo5.Repository.PerguntasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
public class BuscarPerguntasDoBancoService {

    private final PerguntasRepository perguntasRepository;
    private final Random random;

    @Autowired
    public BuscarPerguntasDoBancoService(PerguntasRepository perguntasRepository) {
        this.perguntasRepository = perguntasRepository;
        this.random = new Random();
    }
    public List<PerguntasModel> buscarPerguntasDoBanco() {
        List<PerguntasModel> todasPerguntas = new ArrayList<>();
        for (EixoEnum eixo : EixoEnum.values()) {
            List<PerguntasModel> perguntasEixo = perguntasRepository.findByEixo(eixo);

            Collections.shuffle(perguntasEixo, random);

            todasPerguntas.addAll(perguntasEixo.subList(0, Math.min(perguntasEixo.size(), 10)));
        }
        if (todasPerguntas.size() != 30) {
            throw new RuntimeException("Não foi possível encontrar o número necessário de perguntas");
        }
        return todasPerguntas;
    }
}
