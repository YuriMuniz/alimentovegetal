package br.com.ymg.alimentovegetal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import br.com.ymg.alimentovegetal.R;
import br.com.ymg.alimentovegetal.model.Vegetal;

/**
 * Created by Surfer on 20/03/2018.
 */

public class FilteredCompararVegetalAdapter extends ArrayAdapter<Vegetal> implements Filterable {

    //mAdapter = new ArrayAdapter<String>(getActivity(),
    //android.R.layout.simple_list_item_multiple_choice, mArrayString );

    private FilteredCompararVegetalAdapter.VegetalFilter filter;
    private ArrayList<Vegetal> mOriginalList;
    private ArrayList<Vegetal> mVegetalList;
    private List<String> mArrayString;

    public FilteredCompararVegetalAdapter(Context context, List<Vegetal> vegetais) {

        super(context,0, vegetais);
        this.mVegetalList = new ArrayList<>();
        this.mVegetalList.addAll(vegetais);
        this.mOriginalList = new ArrayList<>();
        this.mOriginalList.addAll(mVegetalList);
    }

    @Override
    public Filter getFilter() {
        if (filter == null){
            filter  = new FilteredCompararVegetalAdapter.VegetalFilter();
        }
        return filter;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Vegetal vegetal = getItem(position);
        ViewHolder holder;

        if(convertView == null){
            convertView = LayoutInflater.from(getContext())
                    .inflate(android.R.layout.simple_list_item_multiple_choice,null);
            holder = new FilteredCompararVegetalAdapter.ViewHolder();
            holder.txtNome = (TextView)convertView.findViewById(android.R.id.text1);
            convertView.setTag(holder);
        }else{
            holder = (FilteredCompararVegetalAdapter.ViewHolder)convertView.getTag();
        }
        holder.txtNome.setText(vegetal.nome);



        return convertView;
    }

    class ViewHolder{
        TextView txtNome;
    }

    private class VegetalFilter extends Filter
    {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            constraint = constraint.toString().toLowerCase();
            FilterResults result = new FilterResults();
            if(constraint != null && constraint.toString().length() > 0)
            {
                ArrayList<Vegetal> filteredItems = new ArrayList<Vegetal>();

                for(int i = 0, l = mOriginalList.size(); i < l; i++)
                {
                    Vegetal vegetal= mOriginalList.get(i);
                    if(vegetal.nome.toString().toLowerCase().contains(constraint))
                        filteredItems.add(vegetal);
                }
                result.count = filteredItems.size();
                result.values = filteredItems;
            }
            else
            {
                synchronized(this)
                {
                    result.values = mOriginalList;
                    result.count = mOriginalList.size();
                }
            }
            return result;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {

            mVegetalList = (ArrayList<Vegetal>)results.values;
            notifyDataSetChanged();
            clear();
            for(int i = 0, l = mVegetalList.size(); i < l; i++)
                add(mVegetalList.get(i));
            notifyDataSetInvalidated();
        }
    }



}
