package br.com.ymg.alimentovegetal.activity;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.List;

import br.com.ymg.alimentovegetal.R;
import br.com.ymg.alimentovegetal.fragment.ComparadoFragment;
import br.com.ymg.alimentovegetal.fragment.CompararFragment;
import br.com.ymg.alimentovegetal.model.Vegetal;

/**
 * Created by Surfer on 21/03/2018.
 */

public class ComparadoActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparado);
        // my_child_toolbar is defined in the layout file
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myChildToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        myChildToolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        List<Vegetal> vegetais =  (List<Vegetal>)getIntent().getSerializableExtra("vegetais");
        ComparadoFragment mf = ComparadoFragment.newInstance(vegetais);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.comparado_activity, mf)
                . commit() ;
    }


}
