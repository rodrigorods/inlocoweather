package com.teste.rodrigo.inlocoweather.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.teste.rodrigo.inlocoweather.R;
import com.teste.rodrigo.inlocoweather.adapter.SurroundingsCitiesAdapter;
import com.teste.rodrigo.inlocoweather.decorator.DividerItemDecorator;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListCityWeatherActivity extends AppCompatActivity {

    public static final String EXTRA_CITIES_ARRAY = "ASD*@&OgUxASD";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.city_weather_list)
    RecyclerView mCityWeatherList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list_city_weather);
        ButterKnife.bind(this);

        mToolbar.setTitle(R.string.title_activity_list_city_weather);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        setSupportActionBar(mToolbar);

        ArrayList cities = getIntent().getParcelableArrayListExtra(EXTRA_CITIES_ARRAY);

        mCityWeatherList.addItemDecoration(new DividerItemDecorator(getBaseContext()));
        mCityWeatherList.setAdapter(new SurroundingsCitiesAdapter(cities));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return false;
    }
}
