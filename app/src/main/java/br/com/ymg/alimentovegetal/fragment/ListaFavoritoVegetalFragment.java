package br.com.ymg.alimentovegetal.fragment;


import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.ymg.alimentovegetal.adapter.VegetalAdapter;
import br.com.ymg.alimentovegetal.data.VegetalDAO;
import br.com.ymg.alimentovegetal.model.Vegetal;
import br.com.ymg.alimentovegetal.util.ClickVegetal;
import br.com.ymg.alimentovegetal.util.VegetalApp;

public class ListaFavoritoVegetalFragment extends ListFragment {

    List<Vegetal> mVegetais;
    VegetalAdapter mAdapter;
    ListView mListView;
    VegetalDAO mDao;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mVegetais = new ArrayList<>();
        setRetainInstance(true);
        mDao = new VegetalDAO(getActivity());
        Bus bus = ((VegetalApp)getActivity().getApplication()).getBus();
        bus.register(this);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(mVegetais.isEmpty()){

            mAdapter = new VegetalAdapter(getActivity(), mVegetais,"Inicio");
            setListAdapter(mAdapter);
            carregarVegetais();
        }

    }

    /*
    @Override
    public void onResume() {
        super.onResume();
        carregarLivros();
    }
    */

    @Override
    public void onViewCreated (View view, Bundle savedInstanceState) {
        mListView = getListView();
    }

    private void carregarVegetais(){
       // VegetalDAO dao = new VegetalDAO(getActivity());
        mVegetais.clear();
       // ListView ltv = getListView();
        if(mDao.listaVegetal().size()==0){


            String[] vazio = new String[] {
                    "Ainda não existe nenhum item adicionado."
            };

            // Create a List from String Array elements
            final List<String> stringVazio = new ArrayList<String>(Arrays.asList(vazio));

            // Create an ArrayAdapter from List
            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                    (getActivity(), android.R.layout.simple_list_item_1, stringVazio);
            mListView.setAdapter(arrayAdapter);

        }else{
            mListView.setAdapter(mAdapter);
            mVegetais.addAll(mDao.listaVegetal());
            mAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Vegetal vegetal = mVegetais.get(position);
        if(getActivity() instanceof ClickVegetal){
            ((ClickVegetal)getActivity()).clicouNoVegetal(vegetal);
        }

    }
    @Subscribe
    public void AtualizarListaFavoritos(Vegetal vegetal){
        carregarVegetais();
    }

}
