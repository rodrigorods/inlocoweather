package com.teste.rodrigo.inlocoweather.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teste.rodrigo.inlocoweather.R;
import com.teste.rodrigo.inlocoweather.model.CityWeather;
import com.teste.rodrigo.inlocoweather.ui.viewholder.CityWeatherViewHolder;

import java.util.List;

public class SurroundingsCitiesAdapter extends RecyclerView.Adapter<CityWeatherViewHolder>{

    private List<CityWeather> mCities;
    private View.OnClickListener onItemClickListener;

    public SurroundingsCitiesAdapter(List<CityWeather> cities){
        this.mCities = cities;
    }

    @Override
    public CityWeatherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        View itemView = mInflater.inflate ( R.layout.cell_city_weather, parent, false );

        if (onItemClickListener != null) {
            itemView.setOnClickListener(onItemClickListener);
        }

        return new CityWeatherViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CityWeatherViewHolder holder, int position) {
        CityWeather cityWeather = mCities.get(position);
        holder.fillContent(cityWeather);
    }

    @Override
    public int getItemCount() {
        return mCities.size();
    }

    public void setOnItemClickListener(View.OnClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
