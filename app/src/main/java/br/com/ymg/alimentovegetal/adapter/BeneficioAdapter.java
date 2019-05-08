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

public class BeneficioAdapter extends ArrayAdapter<Beneficio> {
    public BeneficioAdapter(Context context, List<Beneficio> beneficios) {
        super(context, 0, beneficios);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Beneficio beneficio = getItem(position);
        ViewHolder holder;
        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_beneficio, null);
            holder = new ViewHolder();
            holder.descricao = (TextView)convertView.findViewById(R.id.txtDescricao);
          //  holder.fonte = (TextView)convertView.findViewById(R.id.txtFonte);
            holder.icon = (ImageView)convertView.findViewById(R.id.imgIcon);
            holder.iconModo = (ImageView)convertView.findViewById(R.id.imgIconSeta);
            //holder.txtSeta= (TextView)convertView.findViewById(R.id.txtSeta);
            convertView.setTag(holder);

        }else{
            holder = (ViewHolder)convertView.getTag();
        }
            String descricao = beneficio.descricao.substring(25);
            String primeiraLetra = descricao.substring(0,1);
            primeiraLetra = primeiraLetra.toUpperCase();
            descricao = primeiraLetra + descricao.substring(1);
            holder.descricao.setText(descricao);




      /* if(beneficio.fonte !=  null){
           holder.fonte.setText("Fonte: " + beneficio.fonte);
       }*/

        holder.icon.setImageResource(R.mipmap.ic_alarm_add_black_24dp);
        if(beneficio.modoCompleto!=null){
            holder.iconModo.setImageResource(R.mipmap.ic_keyboard_arrow_right_black_18dp);
        }
       // holder.txtSeta.setText(">");

        return convertView;
    }
    class ViewHolder{
        ImageView icon;
        TextView descricao;
        TextView fonte;
        ImageView iconModo;
        TextView txtSeta;
    }
}
