package br.com.ymg.alimentovegetal.activity;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import br.com.ymg.alimentovegetal.R;
import br.com.ymg.alimentovegetal.fragment.CompararFragment;

/**
 * Created by Surfer on 11/03/2018.
 */

public class CompararActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparar);
        // my_child_toolbar is defined in the layout file
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myChildToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        myChildToolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        CompararFragment mf = new CompararFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.comparar_activity, mf)
                . commit() ;
    }
}
