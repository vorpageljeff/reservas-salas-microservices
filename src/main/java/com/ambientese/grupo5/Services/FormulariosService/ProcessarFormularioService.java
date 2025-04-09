package com.ambientese.grupo5.Services.FormulariosService;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import com.ambientese.grupo5.Model.EmpresaModel;
import com.ambientese.grupo5.Repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ambientese.grupo5.DTO.FormularioRequest;
import com.ambientese.grupo5.Model.Enums.NivelCertificadoEnum;
import com.ambientese.grupo5.Model.Enums.RespostasEnum;
import com.ambientese.grupo5.Model.FormularioModel;
import com.ambientese.grupo5.Model.PerguntasModel;
import com.ambientese.grupo5.Model.RespostaModel;
import com.ambientese.grupo5.Repository.FormularioRepository;
import com.ambientese.grupo5.Repository.PerguntasRepository;
import com.ambientese.grupo5.Repository.RespostaRepository;

import jakarta.transaction.Transactional;

@Service
public class ProcessarFormularioService {

    @Autowired
    private FormularioRepository formularioRepository;

    @Autowired
    private PerguntasRepository perguntasRepository;

    @Autowired
    private RespostaRepository respostaRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Transactional
    public FormularioModel criarProcessarEGerarCertificado(Long empresaId, List<FormularioRequest> formularioRequestList) { 
        if (formularioRequestList.size() != 30) {
            throw new RuntimeException("Número de perguntas inválido");
        }
        if (empresaId == null) {
            throw new RuntimeException("ID da empresa inválido");
        }
        int totalPerguntas = formularioRequestList.size();
        int perguntasConforme = 0;
        int conformeSocial = 0;
        int naoConformeSocial = 0;
        int conformeAmbiental = 0;
        int naoConformeAmbiental = 0;
        int conformeGovernamental = 0;
        int naoConformeGovernamental = 0;
    
        // Verificar as respostas e calcular pontuações
        for (FormularioRequest resposta : formularioRequestList) {
            if (resposta.getRespostaUsuario() != null) {
                if (resposta.getRespostaUsuario() == RespostasEnum.Conforme) {
                    perguntasConforme++;
                    switch (resposta.getPerguntaEixo()) {
                        case Social:
                            conformeSocial++;
                            break;
                        case Ambiental:
                            conformeAmbiental++;
                            break;
                        case Governamental:
                            conformeGovernamental++;
                            break;
                        default:
                            break;
                    }
                } else if (resposta.getRespostaUsuario() == RespostasEnum.NaoConforme) {
                    switch (resposta.getPerguntaEixo()) {
                        case Social:
                            naoConformeSocial++;
                            break;
                        case Ambiental:
                            naoConformeAmbiental++;
                            break;
                        case Governamental:
                            naoConformeGovernamental++;
                            break;
                        default:
                            break;
                    }
                } else if (resposta.getRespostaUsuario() == RespostasEnum.NaoSeAdequa) {
                   totalPerguntas--;
                }
            }
        }
    
        double pontuacaoFinal = (double) perguntasConforme / totalPerguntas * 100.0;
        double pontuacaoAmbiental = (double) conformeAmbiental / (conformeAmbiental + naoConformeAmbiental) * 100.0;
        double pontuacaoSocial = (double) conformeSocial / (conformeSocial + naoConformeSocial) * 100.0;
        double pontuacaoGovernamental = (double) conformeGovernamental / (conformeGovernamental + naoConformeGovernamental) * 100.0;
    
        // Gerar certificado
        NivelCertificadoEnum nivelCertificado = calcularNivelCertificado(pontuacaoFinal);
    
        // Verificar a existência da empresa
        EmpresaModel empresa = empresaRepository.findById(empresaId).orElseThrow(() -> new RuntimeException("Empresa não encontrada"));
    
        // Criar o objeto de modelo de formulário e definir suas propriedades
        FormularioModel formularioModel = new FormularioModel();
        formularioModel.setPontuacaoFinal((int) pontuacaoFinal);
        formularioModel.setPontuacaoSocial((int) pontuacaoSocial);
        formularioModel.setPontuacaoAmbiental((int) pontuacaoAmbiental);
        formularioModel.setPontuacaoGovernamental((int) pontuacaoGovernamental);
        formularioModel.setCertificado(nivelCertificado);
        formularioModel.setEmpresa(empresa);
        formularioModel.setDataRespostas(new Date());
    
        // Salvar o formulário primeiro
        formularioModel = formularioRepository.saveAndFlush(formularioModel);
    
        // Criação e associação das respostas
        List<RespostaModel> respostaModels = new ArrayList<>();
        for (FormularioRequest entry : formularioRequestList) {
            PerguntasModel pergunta = perguntasRepository.findById(entry.getPerguntaId()).orElseThrow(() -> new RuntimeException("Pergunta não encontrada"));
            RespostaModel respostaModel = new RespostaModel(formularioModel, pergunta, entry.getRespostaUsuario());
            respostaModels.add(respostaModel);
        }
    
        // Salvar todas as respostas
        respostaRepository.saveAll(respostaModels);
    
        // Atualizar o formulário com as respostas associadas
        formularioModel.setRespostas(respostaModels);
        return formularioRepository.save(formularioModel);
    }

    NivelCertificadoEnum calcularNivelCertificado(double pontuacaoFinal) {
        if (pontuacaoFinal >= 100) {
            return NivelCertificadoEnum.Ouro;
        } else if (pontuacaoFinal >= 75.1) {
            return NivelCertificadoEnum.Prata;
        } else {
            return NivelCertificadoEnum.Bronze;
        }
    }
}