package br.com.ymg.alimentovegetal.model;

import java.io.Serializable;

/**
 * Created by Surfer on 18/02/2018.
 */

public class Categoria implements Serializable {

    public int id;
    public String nome;

    public Categoria(String nome) {
        this.nome = nome;
        this.id = id;
    }
}
