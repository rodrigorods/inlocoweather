package com.teste.rodrigo.inlocoweather.ui.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.teste.rodrigo.inlocoweather.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CityWeatherViewHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.city_name)
    TextView cityNameTv;

    public CityWeatherViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void fillContent(String cityName){
        cityNameTv.setText(cityName);
    }
}
