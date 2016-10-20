package com.teste.rodrigo.inlocoweather.ui;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.teste.rodrigo.inlocoweather.R;
import com.teste.rodrigo.inlocoweather.model.CityWeather;
import com.teste.rodrigo.inlocoweather.mvp.search_cities.ISearchCitiesPresenter;
import com.teste.rodrigo.inlocoweather.mvp.search_cities.ISearchCitiesView;
import com.teste.rodrigo.inlocoweather.mvp.search_cities.SearchCitiesPresenterImp;

import java.util.List;

public class SearchCityMapActivity extends FragmentActivity implements OnMapReadyCallback, ISearchCitiesView {

    private ISearchCitiesPresenter mPresenter;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_city_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        LatLng sydney = new LatLng(-34, 151);
        this.mPresenter = new SearchCitiesPresenterImp(this, getResources());
        this.mPresenter.searchSurroundingCities(sydney);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void onCitiesLoaded(List<CityWeather> cities) {

    }

    @Override
    public void onCitiesError() {

    }
}
