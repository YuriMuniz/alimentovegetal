package br.com.ymg.alimentovegetal.fragment;


import android.app.ActionBar;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Movie;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import com.squareup.otto.Bus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import br.com.ymg.alimentovegetal.R;
import br.com.ymg.alimentovegetal.activity.VegetalActivity;
import br.com.ymg.alimentovegetal.adapter.FilteredVegetalAdapter;
import br.com.ymg.alimentovegetal.adapter.VegetalAdapter;
import br.com.ymg.alimentovegetal.model.Doenca;
import br.com.ymg.alimentovegetal.model.ProdutoNatural;
import br.com.ymg.alimentovegetal.model.Vegetal;
import br.com.ymg.alimentovegetal.util.ClickVegetal;
import br.com.ymg.alimentovegetal.util.VegetalApp;

public class ListaVegetalFragment extends ListFragment implements SwipeRefreshLayout.OnRefreshListener {

    List<Vegetal> mVegetais;
    VegetalAdapter mAdapter;
    SwipeRefreshLayout mSwipe;
    VegetalTask mTask;
    Doenca mDoenca;
    FilteredVegetalAdapter vegetalAdapter = null;

    private String mSearchQuery;
    private boolean mSearchOpened;
    private EditText mSearchEt;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mVegetais = new ArrayList<>();
        setRetainInstance(true);
        if (getArguments() != null) {
            mDoenca = (Doenca) getArguments().getSerializable("doenca");
        }


        Bus bus = ((VegetalApp) getActivity().getApplication()).getBus();
        bus.register(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_vegetais, null);
        mSwipe = (SwipeRefreshLayout) view.findViewById(R.id.swipe);
        mSwipe.setOnRefreshListener(this);

        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        if (mVegetais.isEmpty()) {

            mAdapter = new VegetalAdapter(
                    getActivity(), mVegetais);
            setListAdapter(mAdapter);
             carregarVegetais();
            }


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_vegetal, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
       // searchView.setSubmitButtonEnabled(true);

        searchView.setOnQueryTextListener(mQueryListener);
       // searchView.setOnCloseListener(mOnCloseListener);
        super.onCreateOptionsMenu(menu, inflater);
    }


    private final SearchView.OnQueryTextListener mQueryListener = new SearchView.OnQueryTextListener() {

        @Override
        public boolean onQueryTextChange(String newText) {
            mAdapter.getFilter().filter(newText);

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
            mAdapter.getFilter().filter(null);
            return false;
        }
    };

    public void novaLista(CharSequence string){
       /* vegetalAdapter = new FilteredVegetalAdapter(
                getActivity(), mVegetais);

        mAdapter.getFilter().filter(string);
        setListAdapter(vegetalAdapter);
        */
        mAdapter.clear();

        mAdapter.getFilter().filter(string);
        mAdapter.notifyDataSetChanged();
    }

    private void carregarVegetais() {


           ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
           NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
           if (networkInfo != null && networkInfo.isConnected()) {
               mSwipe.setRefreshing(true);
               if (mTask == null) {
                   mTask = new VegetalTask();
                   mTask.execute();
                   mSwipe.setRefreshing(false);

               }
           } else {
               Toast.makeText(getActivity(), R.string.msg_erro_conexao, Toast.LENGTH_LONG).show();

           }
           mSwipe.setRefreshing(false);

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Vegetal vegetal = mVegetais.get(position);
        exibirItem(vegetal);

    }


    public void exibirItem(Vegetal vegetal) {
        if (getActivity() instanceof ClickVegetal) {
            ((ClickVegetal) getActivity()).clicouNoVegetal(vegetal);
        }
    }

    public static ListaVegetalFragment newInstance(Doenca doenca){
        Bundle args = new Bundle();
        args.putSerializable("doenca", doenca);

        ListaVegetalFragment lvf = new ListaVegetalFragment();
        lvf.setArguments(args);
        return lvf;
    }


    @Override
    public void onRefresh() {
        carregarVegetais();
    }



    class VegetalTask extends AsyncTask<String, Void, ProdutoNatural> {
        ProdutoNatural produtoNatural;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected ProdutoNatural doInBackground(String... params) {
            OkHttpClient client = new OkHttpClient();

//("https://dl.dropboxusercontent.com/s/5qeciv2aojf0s0s/vegetal.json")


            Request request = new Request.Builder()
            .url("https://dl.dropboxusercontent.com/s/1jqk5h5p4l85gzc/alimentos.json")
                    .build();
            Response response = null;
            try{

                //client.setConnectTimeout(10, TimeUnit.SECONDS);
               // client.setReadTimeout(15, TimeUnit.SECONDS);

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
            if(pn!=null){
                mVegetais.clear();
                for(Vegetal vegetal:pn.vegetais){

                    mVegetais.add(vegetal);

                }
                mAdapter.notifyDataSetChanged();
                if(getResources().getBoolean(R.bool.tablet)){
                    exibirItem(mVegetais.get(0));
                }

            }else {
                Toast.makeText(getActivity(),R.string.msg_erro_dados,Toast.LENGTH_LONG).show();
            }
            mSwipe.setRefreshing(false);

            Collections.sort(mVegetais, new Comparator<Vegetal>() {
                @Override
                public int compare(final Vegetal object1, final Vegetal object2) {
                    return object1.nome.compareTo(object2.nome);
                }
            });

        }

    }










}
