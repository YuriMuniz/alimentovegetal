package br.com.ymg.alimentovegetal.fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import com.squareup.otto.Bus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import br.com.ymg.alimentovegetal.R;
import br.com.ymg.alimentovegetal.adapter.DoencaAdapter;
import br.com.ymg.alimentovegetal.model.Doenca;
import br.com.ymg.alimentovegetal.model.ListaTodasDoencas;
import br.com.ymg.alimentovegetal.model.Vegetal;
import br.com.ymg.alimentovegetal.util.ClickVegetal;
import br.com.ymg.alimentovegetal.util.VegetalApp;


/**
 * Created by yuri on 01/02/16.
 */
public class ListaDoencaFragment  extends ListFragment implements SwipeRefreshLayout.OnRefreshListener {
    List<Doenca> mDoencas;
    DoencaAdapter mAdapter;
    SwipeRefreshLayout mSwipe;
    DoencaTask mTask;

    private String mSearchQuery;
    private boolean mSearchOpened;
    private EditText mSearchEt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDoencas = new ArrayList<>();

        setRetainInstance(true);

        Bus bus = ((VegetalApp) getActivity().getApplication()).getBus();
        bus.register(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_doencas, null);
        mSwipe = (SwipeRefreshLayout) view.findViewById(R.id.swipe2);
        mSwipe.setOnRefreshListener(this);
        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mDoencas.isEmpty()) {

            mAdapter = new DoencaAdapter(
                    getActivity(), mDoencas);
            setListAdapter(mAdapter);
            carregarDoencas();



        }


    }

    private void carregarDoencas() {
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            mSwipe.setRefreshing(true);
            if (mTask == null) {
                mTask = new DoencaTask();
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
        Doenca doenca = mDoencas.get(position);
        exibirItem(doenca);

    }
    public void exibirItem(Doenca doenca) {
        if (getActivity() instanceof ClickVegetal) {
            ((ClickVegetal) getActivity()).clicouNaDoenca(doenca);
        }
    }


    @Override
    public void onRefresh() {
        carregarDoencas();

    }

    class DoencaTask extends AsyncTask<String, Void, ListaTodasDoencas> {
        ListaTodasDoencas listaTodasDoencas;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected ListaTodasDoencas doInBackground(String... params) {
            OkHttpClient client = new OkHttpClient();
//("https://dl.dropboxusercontent.com/s/5qeciv2aojf0s0s/vegetal.json")


            Request request = new Request.Builder()
                    .url("https://dl.dropboxusercontent.com/s/hkst0i2yoth5iso/doenca_teste.json")
                    .build();
            Response response = null;
            try{

               // client.setConnectTimeout(10, TimeUnit.SECONDS);
               // client.setReadTimeout(15, TimeUnit.SECONDS);

                response = client.newCall(request).execute();
                String s = response.body().string();

                Gson gson = new Gson();


                ListaTodasDoencas listaTodasDoencas = gson.fromJson(s, ListaTodasDoencas.class);


                return listaTodasDoencas;

            } catch (Throwable e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ListaTodasDoencas listaTodasDoencas) {
            super.onPostExecute(listaTodasDoencas);
            if(listaTodasDoencas!=null){
                mDoencas.clear();
                for(Doenca doenca:listaTodasDoencas.doencas){

                    mDoencas.add(doenca);

                }
                mAdapter.notifyDataSetChanged();
                if(getResources().getBoolean(R.bool.tablet)){
                    exibirItem(mDoencas.get(0));
                }

            }else {
                Toast.makeText(getActivity(),R.string.msg_erro_dados,Toast.LENGTH_LONG).show();
            }
            mSwipe.setRefreshing(false);

            Collections.sort(mDoencas, new Comparator<Doenca>() {
                @Override
                public int compare(final Doenca object1, final Doenca object2) {
                    return object1.nome.compareTo(object2.nome);
                }
            });

        }

    }
}
