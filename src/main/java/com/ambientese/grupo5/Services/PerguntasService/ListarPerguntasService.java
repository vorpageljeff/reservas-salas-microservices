package com.ambientese.grupo5.Services.PerguntasService;

import com.ambientese.grupo5.Model.PerguntasModel;
import com.ambientese.grupo5.Repository.PerguntasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListarPerguntasService {

    private final PerguntasRepository perguntasRepository;

    @Autowired
    public ListarPerguntasService(PerguntasRepository perguntasRepository) {
        this.perguntasRepository = perguntasRepository;
    }

    public List<PerguntasModel> listarPerguntas() {
        return perguntasRepository.findAll();
    }
}
