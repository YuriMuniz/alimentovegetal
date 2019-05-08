package br.com.ymg.alimentovegetal.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.ymg.alimentovegetal.R;
import br.com.ymg.alimentovegetal.model.Vegetal;

/**
 * Created by Surfer on 21/03/2018.
 */

public class ComparadoFragment extends Fragment {

    private List<Vegetal> mVegetais;

    public static ComparadoFragment newInstance(List<Vegetal> vegetais) {
        Bundle args = new Bundle();
        args.putSerializable("vegetais", (Serializable) vegetais);

        ComparadoFragment cf = new ComparadoFragment();
        cf.setArguments(args);
        return cf;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mVegetais = (List<Vegetal>) getArguments().getSerializable("vegetais");

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        final View layout = inflater.inflate(R.layout.fragment_comparado, null);
        //TextView txt = (TextView)layout.findViewById(R.id.txtTeste);
        for(Vegetal v:mVegetais){
            //txt.setText(v.nome);
        }


        setHasOptionsMenu(true);
        return layout;
    }

}
