package br.com.ymg.alimentovegetal.model;

import java.io.Serializable;

public class Beneficio implements Serializable {
    public String descricao;
    public String modoCompleto;
    public String fonte;
    public String obsCompleta;


    @Override
    public String toString() {
        return "Beneficio{" +
                "descricao='" + descricao + '\'' +
                ", modoCompleto='" + modoCompleto + '\'' +
                ", obsCompleta='" + obsCompleta + '\'' +

                '}';
    }

    public Beneficio(String descricao, String modoCompleto, String fonte, String obsCompleta) {
        this.descricao = descricao;
        this.modoCompleto = modoCompleto;
        this.fonte = fonte;
        this.obsCompleta = obsCompleta;
    }
}
