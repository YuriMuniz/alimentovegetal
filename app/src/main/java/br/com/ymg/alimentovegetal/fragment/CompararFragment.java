package br.com.ymg.alimentovegetal.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import br.com.ymg.alimentovegetal.R;
import br.com.ymg.alimentovegetal.activity.ComparadoActivity;
import br.com.ymg.alimentovegetal.activity.CompararActivity;
import br.com.ymg.alimentovegetal.adapter.FilteredCompararVegetalAdapter;
import br.com.ymg.alimentovegetal.adapter.FilteredVegetalAdapter;
import br.com.ymg.alimentovegetal.model.ProdutoNatural;
import br.com.ymg.alimentovegetal.model.Vegetal;

/**
 * Created by Surfer on 11/03/2018.
 */

public class CompararFragment extends Fragment {

    List<Vegetal> mVegetais;
    ArrayAdapter<String> mAdapter;
    List<String> mArrayString;
    ListView mLtv;
    Button mButton;
    VegetalTask mTask;
   // FilteredCompararVegetalAdapter mFilteredAdapter;
    //List<Vegetal> mVegetaisChecked;
    //int mPosition;
    List<Vegetal> mVegetaisSelecionados;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mVegetais = new ArrayList<>();
        mArrayString = new ArrayList<>();
        mVegetaisSelecionados = new ArrayList<>();


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        carregarVegetais();

    }

    public void compararAlimentos(){
        vegetaisSelecionados();
        Intent intent = new Intent(getActivity(), ComparadoActivity.class);

        intent.putExtra("vegetais", (Serializable) mVegetaisSelecionados);

        startActivity(intent);
    }

    public void vegetaisSelecionados(){
       for(int x=0; x<=mLtv.getCount();x++){
           if(mLtv.isItemChecked(x)){
               for (Vegetal v : mVegetais){
                   if(v.nome.equals(mAdapter.getItem(x))){
                       mVegetaisSelecionados.add(v);
                   }
               }
           }

       }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        final View layout = inflater.inflate(R.layout.fragment_comparar, null);
        mLtv = (ListView) layout.findViewById(R.id.ltvVeg);
        mButton = (Button) layout.findViewById(R.id.buttonComparar);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compararAlimentos();
            }
        });

        setHasOptionsMenu(true);
        return layout;
    }

  /*  @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_comparar, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search_comparar);


        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint(getResources().getString(R.string.pesquisar_vegetal));

        searchView.setOnQueryTextListener(mQueryListener);

        searchView.setOnCloseListener(mOnCloseListener);
        super.onCreateOptionsMenu(menu, inflater);
    }*/

    public void onBackPressed() {
        getActivity().finish();
    }

 /*   private void checkLtv(int position){

        for(int x = 0;x<=mVegetais.size();x++){

        }

    }*/

    private void carregarVegetais() {


        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            mTask = new VegetalTask();
            mTask.execute();

        } else {
            Toast.makeText(getActivity(), R.string.msg_erro_conexao, Toast.LENGTH_LONG).show();

        }

    }


   /* private final SearchView.OnQueryTextListener mQueryListener = new SearchView.OnQueryTextListener() {

        @Override
        public boolean onQueryTextChange(String newText) {

            mFilteredAdapter.getFilter().filter(newText);
            mLtv.setAdapter(mFilteredAdapter);

            mLtv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Vegetal v = (Vegetal)mFilteredAdapter.getItem(position);
                    if(mLtv.isItemChecked(position)){
                        mVegetaisChecked.add(v);
                    }else{
                        mVegetaisChecked.remove(v);
                    }

                   // Toast.makeText(getActivity(), String.valueOf( mLtv.isItemChecked(position))+ v.nome  , Toast.LENGTH_LONG).show();
                    //mPosition=position;
                }
            });
            return true;
        }

        @Override
        public boolean onQueryTextSubmit(String query) {
            mAdapter.getFilter().filter(query);

            return true;
        }

    };
    private final SearchView.OnCloseListener mOnCloseListener = new SearchView.OnCloseListener() {
        @Override public boolean onClose() {

            mLtv.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
            //mLtv.setItemChecked(mPosition,true);
            return false;
        }
    };



    public void newInstanceFilteres(){
        mFilteredAdapter = new FilteredCompararVegetalAdapter(getActivity(), mVegetais);
    }*/


    class VegetalTask extends AsyncTask<String, Void, ProdutoNatural>{

        @Override
        protected ProdutoNatural doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient();
            String url = "https://dl.dropboxusercontent.com/s/vtqhvrg50yd8yu1/alimentos.json?dl=0";
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = null;
            try {

              //  client.setConnectTimeout(10, TimeUnit.SECONDS);
             //   client.setReadTimeout(15, TimeUnit.SECONDS);

                response = client.newCall(request).execute();
                String s = response.body().string();

                Gson gson = new Gson();


                ProdutoNatural pn = gson.fromJson(s, ProdutoNatural.class);

                return pn;

            } catch (Throwable e) {

                e.printStackTrace();
            }
            return null;
        }



        @Override
        protected void onPostExecute(ProdutoNatural pn) {
            super.onPostExecute(pn);
            if (pn != null) {
                for (Vegetal vegetal : pn.vegetais) {

                    mVegetais.add(vegetal);
                    mArrayString.add(vegetal.nome);

                }


                mAdapter = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_list_item_multiple_choice, mArrayString );

                mLtv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                mLtv.setAdapter(mAdapter);


                mLtv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                       /* if(mLtv.getAdapter().equals(mFilteredAdapter)){
                            int item = mLtv.getCheckedItemPosition();
                            mLtv.setAdapter(mAdapter);
                            mLtv.setItemChecked(item,true);
                        }*/
                        //SparseBooleanArray checked = mLtv.getCheckedItemPositions();
                        if(mLtv.getCheckedItemCount()>=2){

                            mButton.setVisibility(View.VISIBLE);
                        }else{
                            mButton.setVisibility(View.GONE);
                        }

                        if (mLtv.getCheckedItemCount()>5){
                            mLtv.setItemChecked(position, false);
                            //mLtv.setChoiceMode(ListView.CHOICE_MODE_NONE);
                            //mLtv.setEnabled(false);
                            //mLtv.setSelector(getResources().getDrawable(android.R.drawable.screen_background_dark_transparent));
                            Toast.makeText(getActivity(), "Limite máximo atingido.", Toast.LENGTH_LONG).show();
                        }

                    }
                });

              /*  mAdapter.notifyDataSetChanged();
                if (getResources().getBoolean(R.bool.tablet)) {
                    exibirItem(mVegetais.get(0));
                }*/

            } else {
                Toast.makeText(getActivity(), R.string.msg_erro_dados, Toast.LENGTH_LONG).show();
            }
            //newInstanceFilteres();
        }
    }

}
