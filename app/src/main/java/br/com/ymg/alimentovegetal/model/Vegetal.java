package br.com.ymg.alimentovegetal.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Vegetal implements Serializable {

    public String capa;
    public String imgDetalhe;
    public String nome;
    public String nomeCientifico;
    public String familia;
    public String fonteComposicao;
    public String formaPreparoComposicao;
    public List<Composicao> composicaoQuimica = new ArrayList<Composicao>();
    public List<Beneficio> beneficios = new ArrayList<Beneficio>();
    public Categoria categoria;

    public Vegetal(){

    }

    public Vegetal(String capa, String imgDetalhe, String nome, String familia, String nomeCientifico, List<Composicao> composicaoQuimica, List<Beneficio> beneficios, Categoria categoria, String fonteComposicao, String formaPreparoComposicao) {
        this.capa = capa;
        this.imgDetalhe = imgDetalhe;
        this.nome = nome;
        this.familia = familia;
        this.nomeCientifico = nomeCientifico;
        this.composicaoQuimica = composicaoQuimica;
        this.beneficios = beneficios;
        this.categoria = categoria;
        this.fonteComposicao = fonteComposicao;
        this.formaPreparoComposicao = formaPreparoComposicao;
    }
    public Vegetal(String nome, Categoria categoria){
        this.nome = nome;
        this.categoria = categoria;
    }

    public Vegetal(String capa, String imgDetalhe, String nome, String familia, String nomeCientifico, Categoria categoria, String fonteComposicao, String formaPreparoComposicao) {
        this.capa = capa;
        this.imgDetalhe = imgDetalhe;
        this.nome = nome;
        this.familia = familia;
        this.nomeCientifico = nomeCientifico;
        this.composicaoQuimica = composicaoQuimica;
        this.beneficios = beneficios;
        this.categoria = categoria;
        this.fonteComposicao = fonteComposicao;
        this.formaPreparoComposicao = formaPreparoComposicao;
    }

    @Override
    public String toString() {
        return "Vegetal{" +
                "capa='" + capa + '\'' +
                ", imgDetalhe='" + imgDetalhe + '\'' +
                ", nome='" + nome + '\'' +
                ", nomeCientifico='" + nomeCientifico + '\'' +
                ", familia='" + familia + '\'' +
                ", composicaoQuimica=" + composicaoQuimica +
                ", beneficios=" + beneficios +
                ", categoria='" + categoria + '\'' +
                '}';
    }


}

