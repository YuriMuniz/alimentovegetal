package br.com.ymg.alimentovegetal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import br.com.ymg.alimentovegetal.R;
import br.com.ymg.alimentovegetal.fragment.DetalheVegetalFragment;
import br.com.ymg.alimentovegetal.fragment.ModoFragment;
import br.com.ymg.alimentovegetal.model.Beneficio;
import br.com.ymg.alimentovegetal.model.Vegetal;

public class ModoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modo_beneficio);

        if(savedInstanceState == null){
            Beneficio beneficio =  (Beneficio)getIntent().getSerializableExtra("beneficio");
            Vegetal vegetal = (Vegetal)getIntent().getSerializableExtra("vegetal");
            ModoFragment mf = ModoFragment.newInstance(beneficio, vegetal);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.modo, mf)
                    . commit() ;
        }

    }


}
