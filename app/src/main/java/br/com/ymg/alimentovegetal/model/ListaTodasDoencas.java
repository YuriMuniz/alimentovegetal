package br.com.ymg.alimentovegetal.model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListaTodasDoencas {

    @SerializedName("doencas")
    public List<Doenca> doencas;
}
