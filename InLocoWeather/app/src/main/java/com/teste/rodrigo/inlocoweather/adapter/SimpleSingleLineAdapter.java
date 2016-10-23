package com.teste.rodrigo.inlocoweather.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teste.rodrigo.inlocoweather.R;
import com.teste.rodrigo.inlocoweather.ui.viewholder.SimpleSingleLineViewHolder;

import java.util.List;

public class SimpleSingleLineAdapter<T> extends RecyclerView.Adapter<SimpleSingleLineViewHolder>{

    private List<T> dataList;
    private View.OnClickListener onItemClickListener;

    public SimpleSingleLineAdapter(List<T> dataList){
        this.dataList = dataList;
    }

    @Override
    public SimpleSingleLineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        View itemView = mInflater.inflate ( R.layout.cell_city_weather, parent, false );

        if (onItemClickListener != null) {
            itemView.setOnClickListener(onItemClickListener);
        }

        return new SimpleSingleLineViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SimpleSingleLineViewHolder holder, int position) {
        holder.fillContent(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void setOnItemClickListener(View.OnClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void updateAllData(List<T> dataList){
        this.dataList = dataList;
        notifyDataSetChanged();
    }
}
