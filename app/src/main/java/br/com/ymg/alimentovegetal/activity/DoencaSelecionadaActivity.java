package br.com.ymg.alimentovegetal.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import br.com.ymg.alimentovegetal.R;
import br.com.ymg.alimentovegetal.fragment.DetalheVegetalFragment;
import br.com.ymg.alimentovegetal.fragment.ListaDoencaFragment;
import br.com.ymg.alimentovegetal.fragment.ListaVegetalFragment;
import br.com.ymg.alimentovegetal.model.Doenca;
import br.com.ymg.alimentovegetal.model.Vegetal;


public class DoencaSelecionadaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doenca_selecionada);
        //getSupportActionBar().hide();



        if(savedInstanceState == null){
            Doenca doenca =  (Doenca)getIntent().getSerializableExtra("doenca");
            ListaVegetalFragment ldf = ListaVegetalFragment.newInstance(doenca);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.doencaSelecionada, ldf)
                    . commit() ;
        }

    }
}
