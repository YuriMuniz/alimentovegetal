package br.com.ymg.alimentovegetal.model;

import java.io.Serializable;

public class Composicao implements Serializable {
    public String descricaoFormatada;
    public String qtd;
    public String sufixoFormatado;


    public Composicao(){

    }
    public Composicao(String descricaoFormatada, String qtd, String sufixoFormatado) {
        this.descricaoFormatada = descricaoFormatada;
        this.qtd = qtd;
        this.descricaoFormatada = descricaoFormatada;
    }

    @Override
    public String toString() {
        return "Nome='" + descricaoFormatada + '\'' +
                ", Quantidade=" + qtd + sufixoFormatado;
    }


}
