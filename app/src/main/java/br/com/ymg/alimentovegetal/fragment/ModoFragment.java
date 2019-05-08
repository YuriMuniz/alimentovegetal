
package br.com.ymg.alimentovegetal.fragment;


import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.com.ymg.alimentovegetal.R;
import br.com.ymg.alimentovegetal.model.Beneficio;
import br.com.ymg.alimentovegetal.model.Vegetal;

public class ModoFragment extends Fragment {
    Beneficio mBeneficio;
    Vegetal mVegetal;
    CoordinatorLayout mCoordinatorLayout;

    public static ModoFragment newInstance(Beneficio beneficio, Vegetal vegetal){
        Bundle args = new Bundle();
        args.putSerializable("beneficio", beneficio);
        args.putSerializable("vegetal", vegetal);

        ModoFragment mf = new ModoFragment();
        mf.setArguments(args);
        return mf;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mBeneficio  =  (Beneficio)getArguments().getSerializable("beneficio");
        mVegetal = (Vegetal)getArguments().getSerializable("vegetal");

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        mBeneficio = (Beneficio) getArguments().getSerializable("beneficio");

        final View layout = inflater.inflate(R.layout.fragment_modo, null);

        Toolbar toolbar = (Toolbar)layout.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mCoordinatorLayout = (CoordinatorLayout)layout.findViewById(R.id.htab_maincontent);
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) layout.findViewById(R.id.collapse_toolbar);
        collapsingToolbar.setTitle(mVegetal.nome.toUpperCase());
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.HeaderTitleStyle);
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.TitleCollapsed);

        ImageView imgDetalhe = (ImageView)layout.findViewById(R.id.imageDetalhe);


        Picasso.with(getActivity())
                .load(mVegetal.imgDetalhe)
                .into(imgDetalhe);


        TextView txtDesc = (TextView)layout.findViewById(R.id.txtDescricao);
        TextView txtModo = (TextView)layout.findViewById(R.id.txtModo);
        TextView txtFonteModo = (TextView)layout.findViewById(R.id.txtFonteModo);
        TextView txtObs = (TextView)layout.findViewById(R.id.txtObs);

        if (mBeneficio.fonte == null) {
            txtFonteModo.setText("Sem fonte cadastrada!");
        }else{
            txtFonteModo.setText(mBeneficio.fonte);
        }

        if(mBeneficio.obsCompleta!=null){
            if(mBeneficio.obsCompleta.equals("")  ){
                CardView cv_ingredient = (CardView) layout.findViewById(R.id.card_viewObs);
                cv_ingredient.setVisibility(View.GONE);
            }else{
                txtObs.setText(mBeneficio.obsCompleta);
            }
        }else{
            CardView cv_ingredient = (CardView) layout.findViewById(R.id.card_viewObs);
            cv_ingredient.setVisibility(View.GONE);
        }


        txtDesc.setText(mBeneficio.descricao);
        txtModo.setText(mBeneficio.modoCompleto);

        return layout;

    }

    public void onBackPressed() {
        getActivity().finish();
    }

}
