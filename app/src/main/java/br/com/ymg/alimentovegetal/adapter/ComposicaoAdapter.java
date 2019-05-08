package br.com.ymg.alimentovegetal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.ymg.alimentovegetal.R;
import br.com.ymg.alimentovegetal.model.Composicao;

public class ComposicaoAdapter extends ArrayAdapter<Composicao> {

    public ComposicaoAdapter(Context context, List<Composicao> composicaoQuimica) {
        super(context, 0, composicaoQuimica);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Composicao composicaoQuimica = getItem(position);
        ViewHolder holder;
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.item_composicao, null);
            holder = new ViewHolder();
            holder.txtNome = (TextView)convertView.findViewById(R.id.txtNome);
            holder.txtQtd = (TextView)convertView.findViewById(R.id.txtQtd);
            holder.txtSufixo = (TextView)convertView.findViewById(R.id.txtSufixo);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        holder.txtNome.setText(composicaoQuimica.descricaoFormatada);
        holder.txtSufixo.setText(composicaoQuimica.sufixoFormatado);

        if(composicaoQuimica.qtd.equals("0")){
            holder.txtSufixo.setText("");
            if(composicaoQuimica.descricaoFormatada.equals("Retinol (Vitamina A)") || composicaoQuimica.descricaoFormatada.equals("Colesterol") ){
                holder.txtQtd.setText("N/A");
            }else{
                holder.txtQtd.setText("Tr");
            }

        }else{
            holder.txtQtd.setText(composicaoQuimica.qtd);
        }


        return convertView;
    }
    class ViewHolder{
        TextView txtNome;
        TextView txtQtd;
        TextView txtSufixo;
    }
}
