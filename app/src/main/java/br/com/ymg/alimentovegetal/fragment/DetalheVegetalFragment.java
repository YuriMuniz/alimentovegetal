package br.com.ymg.alimentovegetal.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Bus;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import br.com.ymg.alimentovegetal.R;
import br.com.ymg.alimentovegetal.activity.ModoActivity;
import br.com.ymg.alimentovegetal.adapter.BeneficioAdapter;
import br.com.ymg.alimentovegetal.adapter.ComposicaoAdapter;
import br.com.ymg.alimentovegetal.data.VegetalDAO;
import br.com.ymg.alimentovegetal.model.Beneficio;
import br.com.ymg.alimentovegetal.model.Composicao;
import br.com.ymg.alimentovegetal.model.Vegetal;
import br.com.ymg.alimentovegetal.util.VegetalApp;

import static br.com.ymg.alimentovegetal.util.Util.setListViewHeightBasedOnChildrenBeneficios;
import static br.com.ymg.alimentovegetal.util.Util.setListViewHeightBasedOnChildrenComposicao;


public class DetalheVegetalFragment extends Fragment {



    List<Composicao> mComposicao;
    ComposicaoAdapter mAdapterComposicao;
    ListView mLtvViewComposicao;
    List<Beneficio> mBeneficios;
    BeneficioAdapter mAdapterBeneficios;
    ListView mLtvViewBeneficios;
    Vegetal mVegetal;
    MenuItem mMenuItemFavorito;
    VegetalDAO mVegetalDAO;
    VegetalDAO mVegetalDAOQtd;
    FloatingActionButton mFab;
    CoordinatorLayout mCoordinatorLayout;

    public static DetalheVegetalFragment newInstance(Vegetal vegetal) {
        Bundle args = new Bundle();
        args.putSerializable("vegetal", vegetal);

        DetalheVegetalFragment dff = new DetalheVegetalFragment();
        dff.setArguments(args);
        return dff;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


            setHasOptionsMenu(true);





    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mVegetalDAO = new VegetalDAO(getActivity());

        boolean favoritoAtualizarInicial = mVegetalDAO.isFavorito(mVegetal);
        atualizarMenuItemFavorito(favoritoAtualizarInicial);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        mVegetal = (Vegetal) getArguments().getSerializable("vegetal");
        /*
        int qtd = mFrutaDAOQtd.qtdBeneficios(mFruta);

        mFrutaDAOQtd =  new FrutaDAO(getActivity());
        Toast.makeText(getActivity(),qtd,Toast.LENGTH_SHORT).show();
        */

        final View layout = inflater.inflate(R.layout.fragment_detalhe_vegetal, null);
        Toolbar toolbar = (Toolbar) layout.findViewById(R.id.toolbar);
        if(getResources().getBoolean(R.bool.fone)){

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }else{
            toolbar.setNavigationIcon(null);
        }

        NestedScrollView nestedScrollView = (NestedScrollView) layout.findViewById(R.id.nested);
        //nestedScrollView.scrollTo(0,0);
        AppBarLayout appBarLayout = (AppBarLayout) layout.findViewById(R.id.htab_appbar);


        mCoordinatorLayout = (CoordinatorLayout) layout.findViewById(R.id.htab_maincontent);
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) layout.findViewById(R.id.collapse_toolbar);

        ViewGroup.LayoutParams params = collapsingToolbar.getLayoutParams();
        params.height = 800;

        if (getResources().getBoolean(R.bool.tablet)) {
            collapsingToolbar.setLayoutParams(params);
        }
        collapsingToolbar.setTitle(mVegetal.nome.toUpperCase());
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.HeaderTitleStyle);
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.TitleCollapsed);




        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());

        mFab = (FloatingActionButton) layout.findViewById(R.id.btn);
        mFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                boolean favoritoAtualizar = mVegetalDAO.isFavorito(mVegetal);

                if (favoritoAtualizar) {
                    mVegetalDAO.exlcuirFruta(mVegetal);
                    Snackbar snackbar = Snackbar.make(
                            mCoordinatorLayout,
                            R.string.item_del_favoritos,
                            Snackbar.LENGTH_SHORT);
                    snackbar.show();
                } else {
                    mVegetalDAO.inserirVegetal(mVegetal);
                    Snackbar snackbar2 = Snackbar.make(
                            mCoordinatorLayout,
                            R.string.item_add_favoritos,
                            Snackbar.LENGTH_SHORT);
                    snackbar2.show();
                }

                atualizarMenuItemFavorito(!favoritoAtualizar);

                Bus bus = ((VegetalApp) getActivity().getApplication()).getBus();
                bus.post(mVegetal);
            }
        });
        ImageView imgDetalhe = (ImageView) layout.findViewById(R.id.imageDetalhe);


        Picasso.with(getActivity())
                .load(mVegetal.imgDetalhe)
                .into(imgDetalhe);


        TextView txtFamilia = (TextView) layout.findViewById(R.id.txtFamilia);
        TextView txtCategoria = (TextView) layout.findViewById(R.id.txtCategoria);
        TextView txtNomeCientifico = (TextView) layout.findViewById(R.id.txtNomeCientifico);
        TextView txtObsAbreviacoes = (TextView) layout.findViewById(R.id.textObsAbreviacoes);
        TextView txtFonteComposicao = (TextView) layout.findViewById(R.id.textViewFonte);
        mLtvViewComposicao = (ListView) layout.findViewById(R.id.listViewPropriedades);
        mLtvViewBeneficios = (ListView) layout.findViewById(R.id.listViewBeneficios);

        TextView txtComposicaoTitulo = (TextView) layout.findViewById(R.id.textViewComposicaoTitulo);
        txtObsAbreviacoes.setText(R.string.obs_abreviacoes);
        txtFonteComposicao.setText("Fonte: " + mVegetal.fonteComposicao);
        txtNomeCientifico.setText(mVegetal.nomeCientifico);
        txtCategoria.setText(mVegetal.categoria.nome);
        txtFamilia.setText(mVegetal.familia);
        txtComposicaoTitulo.setText("Composição (em 100 gramas, " + mVegetal.formaPreparoComposicao + ")"
        );

        mComposicao = new ArrayList<>();
        mComposicao.addAll(mVegetal.composicaoQuimica);
        mAdapterComposicao = new ComposicaoAdapter(getActivity(), mComposicao);
        mLtvViewComposicao.setAdapter(mAdapterComposicao);

        //Beneficio não cadastrado
        ArrayList<String> beneficioSemCadastro = new ArrayList<String>();
        String s = new String("Nenhum benefício cadastrado!");
        beneficioSemCadastro.add(s);
        ArrayAdapter<String> itemsAdapter;

        //
        mBeneficios = new ArrayList<>();

        if (mVegetal.beneficios == null || mVegetal.beneficios.size() == 0) {
            CardView cv_ingredient = (CardView) layout.findViewById(R.id.card_viewBeneficios);
            cv_ingredient.setVisibility(View.GONE);
            itemsAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, beneficioSemCadastro);
            mLtvViewBeneficios.setAdapter(itemsAdapter);


        } else {

            mAdapterBeneficios = new BeneficioAdapter(getActivity(), mBeneficios);
            mBeneficios.addAll(mVegetal.beneficios);
            mLtvViewBeneficios.setAdapter(mAdapterBeneficios);


        }


        mLtvViewBeneficios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Beneficio beneficio = mBeneficios.get(position);
                if (beneficio.modoCompleto != null) {
                    Intent it = new Intent(getActivity(), ModoActivity.class);
                    it.putExtra("beneficio", beneficio);
                    it.putExtra("vegetal", mVegetal);
                    startActivity(it);
                }

            }
        });

  /*      int heightBeneficios = 0;


        if (mVegetal.beneficios==null || mVegetal.beneficios.size()==0){
               heightBeneficios = 100;
        }else{
                heightBeneficios = mBeneficios.size() * 350;
        }*/


      /*  int heightPropreidades=0;

        if (android.os.Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN_MR2) {
            heightPropreidades = mComposicao.size() * 53;
        }

        if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            heightPropreidades = mComposicao.size() * 69;
        }

        if (android.os.Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {
            heightPropreidades = mComposicao.size() * 69;
        }
*/
        setListViewHeightBasedOnChildrenComposicao(mLtvViewComposicao);
        setListViewHeightBasedOnChildrenBeneficios(mLtvViewBeneficios);




       /* ViewGroup.LayoutParams paramsPropriedade = mLtvViewComposicao.getLayoutParams();
        paramsPropriedade.height = heightPropreidades;
        mLtvViewComposicao.setLayoutParams(paramsPropriedade);*/
/*
       ViewGroup.LayoutParams paramsBeneficios = mLtvViewBeneficios.getLayoutParams();
        paramsBeneficios.height = heightBeneficios;
        mLtvViewBeneficios.setLayoutParams(paramsBeneficios);*/


        return layout;
    }


    public void onBackPressed() {
        getActivity().finish();
    }

/*    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_detalhe_vegetal, menu);
        mMenuItemFavorito = menu.findItem(R.id.acao_favorito);
        atualizarMenuItemFavorito(mVegetalDAO.isFavorito(mVegetal));
    }*/

    private void atualizarMenuItemFavorito(boolean favorito) {
        if (!favorito) {
            mFab.setImageResource(R.mipmap.ic_add_black_48dp);
        } else {
            mFab.setImageResource(R.mipmap.ic_clear_black_48dp);
        }
    }
/*    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.acao_favorito){
            boolean favorito = mVegetalDAO.isFavorito(mVegetal);
            if(favorito){
                mVegetalDAO.exlcuirFruta(mVegetal);
                Toast.makeText(getActivity(), "Item removido dos favoritos", Toast.LENGTH_LONG).show();
            }else{
                mVegetalDAO.inserirVegetal(mVegetal);
                Toast.makeText(getActivity(),"Item adicionado ao favoritos", Toast.LENGTH_LONG).show();
            }
            atualizarMenuItemFavorito(!favorito);

            Bus bus = ((VegetalApp)getActivity().getApplication()).getBus();
            bus.post(mVegetal);

        }
        return super.onOptionsItemSelected(item);
    }
*/

}