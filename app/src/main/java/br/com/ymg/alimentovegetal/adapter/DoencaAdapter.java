package br.com.ymg.alimentovegetal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.ymg.alimentovegetal.R;
import br.com.ymg.alimentovegetal.model.Beneficio;
import br.com.ymg.alimentovegetal.model.Doenca;
import br.com.ymg.alimentovegetal.util.Util;

public class DoencaAdapter extends ArrayAdapter<Doenca> {
    public DoencaAdapter(Context context, List<Doenca> doencas) {
        super(context, 0, doencas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Doenca doenca = getItem(position);
        ViewHolder holder;
        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_doenca, null);
            holder = new ViewHolder();
            holder.descricao = (TextView)convertView.findViewById(R.id.txtDescricao);
            holder.fonte = (TextView)convertView.findViewById(R.id.txtFonte);
            holder.icon = (ImageView)convertView.findViewById(R.id.imgIcon);
            convertView.setTag(holder);

        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        holder.descricao.setText(doenca.nome);

        if(doenca.nome !=  null){
            holder.fonte.setText("Fonte: " + doenca.nome);
        }

        holder.icon.setImageResource(Util.getIconDone());


        return convertView;
    }
    class ViewHolder{
        ImageView icon;
        TextView descricao;
        TextView fonte;
    }
}
