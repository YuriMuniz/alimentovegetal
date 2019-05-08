package br.com.ymg.alimentovegetal.model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProdutoNatural {
    @SerializedName("vegetal")
    public List<Vegetal> vegetais;
    public String composicaoEscolhida;
}
