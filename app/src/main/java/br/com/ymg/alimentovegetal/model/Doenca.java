package br.com.ymg.alimentovegetal.model;


import java.io.Serializable;
import java.util.List;

public class Doenca implements Serializable {

    public String nome;
    public List<Vegetal> vegetais;

    public Doenca(String nome, List<Vegetal> vegetais) {
        this.nome = nome;
        this.vegetais = vegetais;
    }


}
