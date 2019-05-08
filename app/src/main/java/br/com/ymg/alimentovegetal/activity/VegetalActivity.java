package br.com.ymg.alimentovegetal.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import java.util.ArrayList;
import java.util.List;

import br.com.ymg.alimentovegetal.R;
import br.com.ymg.alimentovegetal.adapter.FilteredVegetalAdapter;
import br.com.ymg.alimentovegetal.fragment.DetalheVegetalFragment;
import br.com.ymg.alimentovegetal.fragment.ListaDoencaFragment;
import br.com.ymg.alimentovegetal.fragment.ListaFavoritoVegetalFragment;
import br.com.ymg.alimentovegetal.fragment.ListaVegetalFragment;
import br.com.ymg.alimentovegetal.fragment.VegetaisFragment;
import br.com.ymg.alimentovegetal.model.Beneficio;
import br.com.ymg.alimentovegetal.model.Doenca;
import br.com.ymg.alimentovegetal.model.Vegetal;
import br.com.ymg.alimentovegetal.util.ClickVegetal;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class VegetalActivity extends AppCompatActivity  implements ClickVegetal {

    private ListaVegetalFragment listaVegetalFragment;
    private EditText mSearchEt;
    private MenuItem mSearchAction;
    private boolean mSearchOpened;
    private String mSearchQuery;
    FilteredVegetalAdapter vegetalAdapter = null;
    private List<Vegetal> mVegetais;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_vegetal);

        ViewPager viewPager = (ViewPager)findViewById(R.id.viewPager);
        viewPager.setAdapter(new PaginasAdapter(getSupportFragmentManager()));
        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabs);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                160
        );

        params.setMargins(0,160,0,0);
        if (getResources().getBoolean(R.bool.tablet)) {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                tabLayout.setLayoutParams(params);
            }

        }
        tabLayout.setupWithViewPager(viewPager);

        listaVegetalFragment = new ListaVegetalFragment();
        mVegetais = new ArrayList<>();





    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    /*

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vegetal, menu);
        return true;
    }
    */

    /*

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {f
        mSearchAction = menu.findItem(R.id.action_search);
        return super.onPrepareOptionsMenu(menu);
    }
    */


    //Definicao menu procurar ou fechar edittext e seus respectivos metodos

    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            if (mSearchOpened) {
                closeSearchBar();
            } else {
                openSearchBar(mSearchQuery);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    */


    @Override
    public void clicouNoVegetal(Vegetal vegetal) {
        if(getResources().getBoolean(R.bool.fone)) {
            Intent it = new Intent(this, DetalheVegetalActivity.class);
            it.putExtra("vegetal", vegetal);
            startActivity(it);
        }else{

            DetalheVegetalFragment dff   = DetalheVegetalFragment.newInstance(vegetal);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.detalhe,dff);
            //ft.addToBackStack(null);
            ft.commit();


            /*getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.detalhe, dff)
                    .commit(); ;*/
         }
    }

    @Override
    public void clicouNaDoenca(Doenca doenca) {
        if(getResources().getBoolean(R.bool.fone)) {
            Intent it = new Intent(this, DoencaSelecionadaActivity.class);
            it.putExtra("doenca", doenca);
            startActivity(it);
        }else{
            ListaVegetalFragment ldf   =  ListaVegetalFragment.newInstance(doenca);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.doencaSelecionada, ldf)
                    .commit() ;
        }
    }

    @Override
    public void clicouNoBeneficio(Beneficio beneficio, Vegetal vegetal) {
            Intent it = new Intent(this, ModoActivity.class);
            it.putExtra("beneficio", beneficio);
            it.putExtra("vegetal", vegetal);
            startActivity(it);
    }


    private class PaginasAdapter extends FragmentPagerAdapter {

        public PaginasAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position==0){
                return new VegetaisFragment();
            }else{
                //return new ListaFavoritoVegetalFragment();

                return new ListaFavoritoVegetalFragment();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String abaVegetais = getResources().getString(R.string.vegetais);
            String abaFavoritos = getResources().getString(R.string.favorito);
            if(position==0){
                return abaVegetais;
            }else{
                return abaFavoritos;
            }
        }
    }
}
