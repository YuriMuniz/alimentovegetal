package br.com.ymg.alimentovegetal.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
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
import java.util.List;
import java.util.concurrent.TimeUnit;

import br.com.ymg.alimentovegetal.R;
import br.com.ymg.alimentovegetal.activity.CompararActivity;
import br.com.ymg.alimentovegetal.activity.VegetalActivity;
import br.com.ymg.alimentovegetal.adapter.FilteredVegetalAdapter;
import br.com.ymg.alimentovegetal.adapter.VegetalAdapter;
import br.com.ymg.alimentovegetal.model.Doenca;
import br.com.ymg.alimentovegetal.model.ProdutoNatural;
import br.com.ymg.alimentovegetal.model.Vegetal;
import br.com.ymg.alimentovegetal.util.ClickVegetal;
import br.com.ymg.alimentovegetal.util.VegetalApp;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

//import android.support.v7.internal.view.ContextThemeWrapper;


public class VegetaisFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener  {

    List<Vegetal> mVegetais;
    FilteredVegetalAdapter mFilteredAdapter;
    VegetalAdapter mAdapter;
    ListView mListView;
    Button mButton;
    VegetalTask mTask;
    private int mProgressStatus = 0;
    private ProgressDialog mProgressDialog ;
    Doenca mDoenca;
    private AlertDialog mAlerta;
    SwipeRefreshLayout mSwipe;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mVegetais = new ArrayList<>();
        setRetainInstance(true);
        //mAdapter.mComposicaoEscolhida="Inicio";
        Bus bus = ((VegetalApp) getActivity().getApplication()).getBus();
        bus.register(this);



    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View layout = inflater.inflate(R.layout.vegetal_fragment, null);
        mListView = (ListView) layout.findViewById(R.id.vegetaisLista);

        mButton = (Button) layout.findViewById(R.id.button2);

        mSwipe = (SwipeRefreshLayout) layout.findViewById(R.id.swipe);
        mSwipe.setOnRefreshListener(this);
        final String[] composicaoEscolhida = new String[1];
        composicaoEscolhida[0] = "Inicio";



        mButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Cria o gerador do AlertDialog
                final AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(),R.style.AlertDialogCustom ));
                builder.setIcon(R.mipmap.ic_swap_vert_black_48dp);
                builder.setTitle("Ordenar");
               // ListView ltv = mAlerta.getListView();
                setHasOptionsMenu(true);


                builder.setItems(R.array.nomes_composicao_ordernar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        if(which==0){

                            new VegetalTask().execute("+ Calorias");

                            mAdapter.mComposicaoEscolhida = "Calorias";
                            mAdapter.notifyDataSetChanged();

                        }
                        if(which==1){
                            new VegetalTask().execute("- Calorias");
                            mAdapter.mComposicaoEscolhida = "Calorias";
                            mAdapter.notifyDataSetChanged();
                        }
                        if(which==2){
                            new VegetalTask().execute("+ Proteina");
                            mAdapter.mComposicaoEscolhida = "Proteína";
                            mAdapter.notifyDataSetChanged();
                        }

                        if(which==3){
                            new VegetalTask().execute("- Proteina");
                            mAdapter.mComposicaoEscolhida = "Proteína";
                            mAdapter.notifyDataSetChanged();
                        }


                        if(which==4){
                            new VegetalTask().execute("+ Lipideos");
                            mAdapter.mComposicaoEscolhida = "Lipídeos";
                            mAdapter.notifyDataSetChanged();
                        }
                        if(which==5){
                            new VegetalTask().execute("- Lipideos");
                            mAdapter.mComposicaoEscolhida = "Lipídeos";
                            mAdapter.notifyDataSetChanged();
                        }

                        if(which==6){
                            new VegetalTask().execute("+ Carboidrato");
                            mAdapter.mComposicaoEscolhida = "Carboidrato";
                            mAdapter.notifyDataSetChanged();
                        }

                        if(which==7){
                            new VegetalTask().execute("- Carboidrato");
                            mAdapter.mComposicaoEscolhida = "Carboidrato";
                            mAdapter.notifyDataSetChanged();
                        }
                        if(which==8){

                            new VegetalTask().execute("+ Fibra Alimentar");
                            mAdapter.mComposicaoEscolhida = "Fibra Alimentar";
                            mAdapter.notifyDataSetChanged();
                        }

                        if(which==9){
                            new VegetalTask().execute("- Fibra Alimentar");
                            mAdapter.mComposicaoEscolhida = "Fibra Alimentar";
                            mAdapter.notifyDataSetChanged();
                        }

                        if(which==10){
                            new VegetalTask().execute("+ Calcio");
                            mAdapter.mComposicaoEscolhida = "Cálcio";
                            mAdapter.notifyDataSetChanged();
                        }

                        if(which==11){
                            new VegetalTask().execute("- Calcio");
                            mAdapter.mComposicaoEscolhida = "Cálcio";
                            mAdapter.notifyDataSetChanged();
                        }

                        if(which==12){
                            new VegetalTask().execute("+ Magnesio");
                            mAdapter.mComposicaoEscolhida = "Magnésio";
                            mAdapter.notifyDataSetChanged();
                        }

                        if(which==13){
                            new VegetalTask().execute("- Magnesio");
                            mAdapter.mComposicaoEscolhida = "Magnésio";
                            mAdapter.notifyDataSetChanged();
                        }

                       /* if(which==13){
                            new VegetalTask().execute("Zinco");
                            Toast.makeText(getActivity(),"Zinco", Toast.LENGTH_SHORT).show();
                        }*/

                        if(which==14){
                            new VegetalTask().execute("+ Manganes");
                            mAdapter.mComposicaoEscolhida = "Manganês";
                            mAdapter.notifyDataSetChanged();
                        }

                        if(which==15){
                            new VegetalTask().execute("- Manganes");
                            mAdapter.mComposicaoEscolhida = "Manganês";
                            mAdapter.notifyDataSetChanged();
                        }

                        if(which==16){
                            new VegetalTask().execute("+ Fosforo");
                            mAdapter.mComposicaoEscolhida = "Fósforo";
                            mAdapter.notifyDataSetChanged();
                        }

                        if(which==17){
                            new VegetalTask().execute("- Fosforo");
                            mAdapter.mComposicaoEscolhida = "Fósforo";
                            mAdapter.notifyDataSetChanged();
                        }

                        if(which==18){
                            new VegetalTask().execute("+ Ferro");
                            mAdapter.mComposicaoEscolhida = "Ferro";
                            mAdapter.notifyDataSetChanged();
                        }

                        if(which==19){
                            new VegetalTask().execute("- Ferro");
                            mAdapter.mComposicaoEscolhida = "Ferro";
                            mAdapter.notifyDataSetChanged();
                        }


                        if(which==20){
                            new VegetalTask().execute("+ Sodio");
                            mAdapter.mComposicaoEscolhida = "Sódio";
                            mAdapter.notifyDataSetChanged();
                        }
                        if(which==21){
                            new VegetalTask().execute("- Sodio");
                            mAdapter.mComposicaoEscolhida = "Sódio";
                            mAdapter.notifyDataSetChanged();
                        }
                        if(which==22){
                            new VegetalTask().execute("+ Potassio");
                            mAdapter.mComposicaoEscolhida = "Potássio";
                            mAdapter.notifyDataSetChanged();
                        }
                        if(which==23){
                            new VegetalTask().execute("- Potassio");
                            mAdapter.mComposicaoEscolhida = "Potássio";
                            mAdapter.notifyDataSetChanged();
                        }
                        if(which==24){
                            new VegetalTask().execute("+ Cobre");
                            mAdapter.mComposicaoEscolhida = "Cobre";
                            mAdapter.notifyDataSetChanged();
                        }
                        if(which==25){
                            new VegetalTask().execute("- Cobre");
                            mAdapter.mComposicaoEscolhida = "Cobre";
                            mAdapter.notifyDataSetChanged();
                        }
                        if(which==26){

                            new VegetalTask().execute("+ Zinco");
                            mAdapter.mComposicaoEscolhida = "Zinco";
                            mAdapter.notifyDataSetChanged();
                        }
                        if(which==27){
                            new VegetalTask().execute("- Zinco");
                            mAdapter.mComposicaoEscolhida = "Zinco";
                            mAdapter.notifyDataSetChanged();
                        }


                        if(which==28){
                            new VegetalTask().execute("+ VitaminaB1");
                            mAdapter.mComposicaoEscolhida = "Tiamina (Vitamina B1)";
                            mAdapter.notifyDataSetChanged();
                        }

                        if(which==29){
                            new VegetalTask().execute("- VitaminaB1");
                            mAdapter.mComposicaoEscolhida = "Tiamina (Vitamina B1)";
                            mAdapter.notifyDataSetChanged();
                        }

                        if(which==30){
                            new VegetalTask().execute("+ VitaminaB2");
                            mAdapter.mComposicaoEscolhida = "Riboflavina (Vitamina B2)";
                            mAdapter.notifyDataSetChanged();
                        }

                        if(which==31){
                            mAdapter.mComposicaoEscolhida = "Riboflavina (Vitamina B2)";
                            mAdapter.notifyDataSetChanged();
                        }

                        if(which==32){
                            new VegetalTask().execute("+ VitaminaB6");
                            mAdapter.mComposicaoEscolhida = "Piridoxina (Vitamina B6)";
                            mAdapter.notifyDataSetChanged();
                        }
                        if(which==33){
                            mAdapter.mComposicaoEscolhida = "Piridoxina (Vitamina B6)";
                            mAdapter.notifyDataSetChanged();
                        }

                        if(which==34){
                            new VegetalTask().execute("+ VitaminaB3");
                            mAdapter.mComposicaoEscolhida = "Niacina (Vitamina B3)";
                            mAdapter.notifyDataSetChanged();
                        }

                        if(which==35){
                            new VegetalTask().execute("- VitaminaB3");
                            mAdapter.mComposicaoEscolhida = "Niacina (Vitamina B3)";
                            mAdapter.notifyDataSetChanged();
                        }

                        if(which==36){
                            new VegetalTask().execute("+ VitaminaC");
                            mAdapter.mComposicaoEscolhida = "Vitamina C";
                            mAdapter.notifyDataSetChanged();
                        }
                        if(which==37){
                            new VegetalTask().execute("- VitaminaC");
                            mAdapter.mComposicaoEscolhida = "Vitamina C";
                            mAdapter.notifyDataSetChanged();
                        }



                    }
                });


                builder.setNegativeButton("Fechar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                      mAlerta.dismiss();
                    }
                });



                mAlerta = builder.create();
                ListView listView=mAlerta.getListView();
                listView.setDivider(new ColorDrawable(Color.BLACK));
                listView.setDividerHeight(1);

                mAlerta.show();
            }
        });


        mAdapter = new VegetalAdapter(
                getActivity(), mVegetais, composicaoEscolhida[0]);

        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Vegetal vegetal = mVegetais.get(position);
                exibirItem(vegetal);
                }

        });



        setHasOptionsMenu(true);
        return layout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        if (mVegetais.isEmpty()){
            carregarVegetais();
        }


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        if (getResources().getBoolean(R.bool.fone)) {
            inflater.inflate(R.menu.menu_vegetal, menu);
            MenuItem searchItem = menu.findItem(R.id.action_search);
            // MenuItem more = menu.findItem(R.id.more);

            SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
            searchView.setQueryHint(getResources().getString(R.string.pesquisar_vegetal));

            searchView.setOnQueryTextListener(mQueryListener);

            searchView.setOnCloseListener(mOnCloseListener);
        }

        super.onCreateOptionsMenu(menu, inflater);
    }


/*    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.more:
                compararAlimentos();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }*/

    public void compararAlimentos(){
        Intent intent = new Intent(getActivity(), CompararActivity.class);
        startActivity(intent);
    }



    private final SearchView.OnQueryTextListener mQueryListener = new SearchView.OnQueryTextListener() {

        @Override
        public boolean onQueryTextChange(String newText) {

            mFilteredAdapter.getFilter().filter(newText);

            mListView.setAdapter(mFilteredAdapter);
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

            mListView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();

            return false;
        }
    };


    public void exibirItem(Vegetal vegetal) {
        if (getActivity() instanceof ClickVegetal) {
            ((ClickVegetal) getActivity()).clicouNoVegetal(vegetal);
        }
    }


    /*



     */

    public void newInstanceFilteres(){

        mFilteredAdapter = new FilteredVegetalAdapter(getActivity(), mVegetais);

    }

    private void carregarVegetais() {


        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            mSwipe.setRefreshing(true);

                mTask = new VegetalTask();
                mTask.execute("Inicio");
                mSwipe.setRefreshing(false);



        } else {
            Toast.makeText(getActivity(), R.string.msg_erro_conexao, Toast.LENGTH_LONG).show();

        }
        mSwipe.setRefreshing(false);

    }

    @Override
    public void onRefresh() {


        carregarVegetais();
    }


    class VegetalTask extends AsyncTask<String, Void, ProdutoNatural> {
        ProdutoNatural produtoNatural;
        private ProgressDialog pdia;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mVegetais.clear();
            mListView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
            pdia = new ProgressDialog(getActivity());
            pdia.setMessage("Carregando...");
            pdia.show();


        }

        @Override
        protected ProdutoNatural doInBackground(String... params) {
            OkHttpClient client = new OkHttpClient();
            String url = "";
            //mTextView.setText(params[0]);
            //mListView.smoothScrollToPosition(0);
            //String p = params[0].toString();
            // https://www.dropbox.com/s/fh5u6mpky6qst27/alimentos-order-zinco.json?dl=0
        //("https://dl.dropboxusercontent.com/s/5qeciv2aojf0s0s/vegetal.json")
        //("https://dl.dropboxusercontent.com/s/x8yo66zkt8djpfm/vegetal_teste.json")
            url="https://dl.dropboxusercontent.com/s/vtqhvrg50yd8yu1/alimentos.json?dl=0";
           /* if(params[0].equals("Inicio")){
                mAdapter.mComposicaoEscolhida="Inicio";
                mAdapter.notifyDataSetChanged();
            }*/

            if(params[0].equals("- Calcio")){
                url="https://dl.dropboxusercontent.com/s/55785axavxfly52/calcio-.json?dl=0";
            }
            if(params[0].equals("+ Calcio")){
                url="https://dl.dropboxusercontent.com/s/zgzkq4hk8anilgu/calcio%2B.json?dl=0";
            }
            if(params[0].equals("+ Calorias")){
                url="https://dl.dropboxusercontent.com/s/2y9o866plyq7h0t/calorias%2B.json?dl=0";
            }
            if(params[0].equals("- Calorias")){
                url="https://dl.dropboxusercontent.com/s/xc6599fgj7cdb3r/calorias-.json?dl=0";
            }
            if(params[0].equals("+ Carboidrato")){
                url="https://dl.dropboxusercontent.com/s/4bsvwrv7auqvqcc/carboidrato%2B.json?dl=0";
            }
            if(params[0].equals("- Carboidrato")){
                url="https://dl.dropboxusercontent.com/s/egpezt6jwp9u32e/carboidrato-.json?dl=0";
            }

            if(params[0].equals("+ Cobre")){
                url="https://dl.dropboxusercontent.com/s/sb8ctwz6mar4atb/cobre%2B.json?dl=0";
            }

            if(params[0].equals("- Cobre")){
                url="https://dl.dropboxusercontent.com/s/bxmsallno1jxxe4/cobre-.json?dl=0";
            }

            if(params[0].equals("+ Ferro")){
                url="https://dl.dropboxusercontent.com/s/dnnd3qo8swna2wr/ferro%2B.json?dl=0";
            }

            if(params[0].equals("- Ferro")){
                url="https://dl.dropboxusercontent.com/s/ufkr4r3ls8zvc8f/ferro-.json?dl=0";
            }

            if(params[0].equals("+ Fibra Alimentar")){

                url="https://dl.dropboxusercontent.com/s/pw2pz2kb671emy5/fibraalimentar%2B.json?dl=0";
            }

            if(params[0].equals("- Fibra Alimentar")){
                url="https://dl.dropboxusercontent.com/s/j7q5w8z20vqukpi/fibraalimentar-.json?dl=0";
            }

            if(params[0].equals("+ Fosforo")){
                url="https://dl.dropboxusercontent.com/s/stq433l43pdk07x/fosforo%2B.json?dl=0";
            }

            if(params[0].equals("- Fosforo")){
                url="https://dl.dropboxusercontent.com/s/cpu3wkb4lb82ctd/fosforo-.json?dl=0";
            }

            if(params[0].equals("+ Lipideos")){
                url="https://dl.dropboxusercontent.com/s/qgx6s2dv0f30ajs/lipideos%2B.json?dl=0";
            }
            if(params[0].equals("- Lipideos")){
                url="https://dl.dropboxusercontent.com/s/5ak3l19tl2hl2f4/lipideos-.json?dl=0";
            }


            if(params[0].equals("+ Magnesio")){
                url="https://dl.dropboxusercontent.com/s/dcvoknh01994lpf/magnesio%2B.json?dl=0";
            }

            if(params[0].equals("- Magnesio")){
                url="https://dl.dropboxusercontent.com/s/izs91qo28ndmqnm/magnesio-.json?dl=0";
            }

            if(params[0].equals("+ Manganes")){
                url="https://dl.dropboxusercontent.com/s/79ek1qspdk24mlu/manganes%2B.json?dl=0";
            }

            if(params[0].equals("- Manganes")){
                url="https://dl.dropboxusercontent.com/s/unucipq99f8daiw/manganes-.json?dl=0";
            }

            if(params[0].equals("+ Potassio")){
                url="https://dl.dropboxusercontent.com/s/3stw04m3n06fsk0/potassio%2B.json?dl=0";
            }
            if(params[0].equals("- Potassio")){
                url="https://dl.dropboxusercontent.com/s/l49z7y4l33bfam7/potassio-.json?dl=0";
            }

            if(params[0].equals("+ Proteina")){
                url="https://dl.dropboxusercontent.com/s/j9dt56r6m1hrx60/proteina%2B.json?dl=0";
            }

            if(params[0].equals("- Proteina")){
                url="https://dl.dropboxusercontent.com/s/pndv3wfgu5rdp1j/proteina-.json?dl=0";
            }

            if(params[0].equals("+ Sodio")){
                url="https://dl.dropboxusercontent.com/s/jkps9b4srb8a8rc/sodio%2B.json?dl=0";
            }

            if(params[0].equals("- Sodio")){
                url="https://dl.dropboxusercontent.com/s/221fm9nqwnil9un/sodio-.json?dl=0";
            }

            if(params[0].equals("+ VitaminaB1")){
                url="https://dl.dropboxusercontent.com/s/nzojp1mxeqeajrk/vitaminab1%2B.json?dl=0";
            }

            if(params[0].equals("- VitaminaB1")){
                url="https://dl.dropboxusercontent.com/s/4zfy1xzekbwblgq/vitaminab1-.json?dl=0";
            }

            if(params[0].equals("+ VitaminaB2")){
                url="https://dl.dropboxusercontent.com/s/kkoyp47z5ehywjs/vitaminab2%2B.json?dl=0";
            }

            if(params[0].equals("- VitaminaB2")){
                url="https://dl.dropboxusercontent.com/s/fb6ie6fn9pumdir/vitaminab2-.json?dl=0";
            }

            if(params[0].equals("+ VitaminaB3")){
                url="https://dl.dropboxusercontent.com/s/91vssshujzymp3d/vitaminab3%2B.json?dl=0";
            }

            if(params[0].equals("- VitaminaB3")){
                url="https://dl.dropboxusercontent.com/s/yrx8bwx48ei8ydx/vitaminab3-.json?dl=0";
            }

            if(params[0].equals("+ VitaminaB6")){
                url="https://dl.dropboxusercontent.com/s/5xalmhheeyp94zr/vitaminab6%2B.json?dl=0";
            }

            if(params[0].equals("- VitaminaB6")){
                url="https://dl.dropboxusercontent.com/s/yzcrlgg3ks2cm3r/vitaminab6-.json?dl=0";
            }

            if(params[0].equals("+ VitaminaC")){
                url="https://dl.dropboxusercontent.com/s/cqdl6139ee6pn5p/vitaminac%2B.json?dl=0";
            }

            if(params[0].equals("- VitaminaC")){
                url="https://dl.dropboxusercontent.com/s/oqza05ykwtwxyaq/vitaminac-.json?dl=0";
            }


            if(params[0].equals("+ Zinco")){
                url="https://dl.dropboxusercontent.com/s/ec6rtjs94obx4wf/zinco%2B.json?dl=0";
           }

            if(params[0].equals("- Zinco")){
                url="https://dl.dropboxusercontent.com/s/w5jac7bbn7ojhm1/zinco-.json?dl=0";
            }


            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = null;
            try {

                //client.setConnectTimeout(10, TimeUnit.SECONDS);
               // client.setReadTimeout(15, TimeUnit.SECONDS);

                response = client.newCall(request).execute();
                String s = response.body().string();

                Gson gson = new Gson();


                ProdutoNatural pn = gson.fromJson(s, ProdutoNatural.class);
                pn.composicaoEscolhida = params[0];

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
                if(!pn.composicaoEscolhida.equals("Inicio")){
                    if(pn.composicaoEscolhida.contains("+")){
                        mButton.setCompoundDrawablesWithIntrinsicBounds( R.mipmap.ic_sort_black_48dp, 0, 0, 0);
                    }
                    if(pn.composicaoEscolhida.contains("-")){
                        mButton.setCompoundDrawablesWithIntrinsicBounds( R.mipmap.ic_sort_black_asc_48dp, 0, 0, 0);
                    }

                   // String ce =  pn.composicaoEscolhida.substring(1,pn.composicaoEscolhida.length());
                }

                if(pn.composicaoEscolhida.equals("Inicio")){
                    mButton.setText("Ordenar");
                    mButton.setCompoundDrawablesWithIntrinsicBounds( R.mipmap.ic_swap_vert_black_48dp, 0, 0, 0);
                }else{
                    mButton.setText(pn.composicaoEscolhida);
                }
            }

            pdia.dismiss();
            if (pn != null) {
                mVegetais.clear();
                for (Vegetal vegetal : pn.vegetais) {

                    mVegetais.add(vegetal);

                }
                mAdapter.notifyDataSetChanged();
                if (getResources().getBoolean(R.bool.tablet)) {

                    exibirItem(mVegetais.get(0));

                }

            } else {
                Toast.makeText(getActivity(), R.string.msg_erro_dados, Toast.LENGTH_LONG).show();
            }
            mSwipe.setRefreshing(false);
         /*   Collections.sort(mVegetais, new Comparator<Vegetal>() {
                @Override
                public int compare(final Vegetal object1, final Vegetal object2) {
                    return object1.nome.compareTo(object2.nome);
                }
            });*/
            newInstanceFilteres();
        }

    }
}