package br.com.ymg.alimentovegetal.activity;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import br.com.ymg.alimentovegetal.R;
import br.com.ymg.alimentovegetal.fragment.DetalheVegetalFragment;
import br.com.ymg.alimentovegetal.model.Vegetal;

public class DetalheVegetalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_vegetal);
        //getSupportActionBar().hide();

        if(savedInstanceState == null){
            Vegetal vegetal =  (Vegetal)getIntent().getSerializableExtra("vegetal");
            DetalheVegetalFragment dff = DetalheVegetalFragment.newInstance(vegetal);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.detalhe, dff)
                    . commit() ;
        }


    }
}
