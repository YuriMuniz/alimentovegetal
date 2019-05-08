package br.com.ymg.alimentovegetal.util;

import br.com.ymg.alimentovegetal.model.Beneficio;
import br.com.ymg.alimentovegetal.model.Doenca;
import br.com.ymg.alimentovegetal.model.Vegetal;

/**
 * Created by yuri on 13/12/15.
 */
public interface ClickVegetal {
    void clicouNoVegetal(Vegetal vegetal);
    void clicouNaDoenca(Doenca doenca);
    void clicouNoBeneficio(Beneficio beneficio, Vegetal vegetal);
}
