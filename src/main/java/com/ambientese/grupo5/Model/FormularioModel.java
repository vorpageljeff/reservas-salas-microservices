package com.ambientese.grupo5.Model;

import com.ambientese.grupo5.Model.Enums.NivelCertificadoEnum;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Formulario")
public class FormularioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "formulario", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonManagedReference
    private List<RespostaModel> respostas;

    @Column(name = "certificado")
    @Enumerated(EnumType.STRING)
    private NivelCertificadoEnum certificado;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "empresa_id")
    private EmpresaModel empresa;

    @Column(name = "pontuacao_final")
    private Integer pontuacaoFinal;

    @Column(name = "pontuacao_social")
    private Integer pontuacaoSocial;

    @Column(name = "pontuacao_ambiental")
    private Integer pontuacaoAmbiental;

    @Column(name = "pontuacao_governamental")
    private Integer pontuacaoGovernamental;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_respostas")
    private Date dataRespostas;

    public Integer getPontuacaoFinal() {
        return pontuacaoFinal;
    }

    public void setPontuacaoFinal(Integer pontuacaoFinal) {
        this.pontuacaoFinal = pontuacaoFinal;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public NivelCertificadoEnum getCertificado() {
        return certificado;
    }

    public void setCertificado(NivelCertificadoEnum certificado) {
        this.certificado = certificado;
    }

    public EmpresaModel getEmpresa() {
        return empresa;
    }

    public void setEmpresa(EmpresaModel empresa) {
        this.empresa = empresa;
    }

    public List<RespostaModel> getRespostas() {
        return respostas;
    }

    public void setRespostas(List<RespostaModel> respostas) {
        this.respostas = respostas;
    }

    public Integer getPontuacaoSocial() {
        return pontuacaoSocial;
    }

    public void setPontuacaoSocial(Integer pontuacaoSocial) {
        this.pontuacaoSocial = pontuacaoSocial;
    }

    public Integer getPontuacaoAmbiental() {
        return pontuacaoAmbiental;
    }

    public void setPontuacaoAmbiental(Integer pontuacaoAmbiental) {
        this.pontuacaoAmbiental = pontuacaoAmbiental;
    }

    public Integer getPontuacaoGovernamental() {
        return pontuacaoGovernamental;
    }

    public void setPontuacaoGovernamental(Integer pontuacaoGovernamental) {
        this.pontuacaoGovernamental = pontuacaoGovernamental;
    }

    public Date getDataRespostas() {
        return dataRespostas;
    }

    public void setDataRespostas(Date dataRespostas) {
        this.dataRespostas = dataRespostas;
    }
}
