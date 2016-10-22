package com.teste.rodrigo.inlocoweather.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.teste.rodrigo.inlocoweather.R;
import com.teste.rodrigo.inlocoweather.model.CityWeather;
import com.teste.rodrigo.inlocoweather.mvp.search_cities.ISearchCitiesPresenter;
import com.teste.rodrigo.inlocoweather.mvp.search_cities.ISearchCitiesView;
import com.teste.rodrigo.inlocoweather.mvp.search_cities.SearchCitiesPresenterImp;
import com.teste.rodrigo.inlocoweather.util.ConnectivityUtil;
import com.teste.rodrigo.inlocoweather.util.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchCityMapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener, ISearchCitiesView {

    private static final String EXTRA_LOCATION = "AAW7U@al";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.search_cities_weather_btn)
    Button mSearchSurroundingsBtn;

    private ISearchCitiesPresenter mPresenter;
    private GoogleMap mMap;
    private Marker currentDisplayedMarker;

    private ProgressDialog progressDialog;

    private LatLng savedLocationLatLng = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_city_map);
        ButterKnife.bind(this);

        mToolbar.setTitle(R.string.title_activity_search_city_map);
        setSupportActionBar(mToolbar);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        this.mPresenter = new SearchCitiesPresenterImp(this, getResources());
        this.progressDialog = new ProgressDialog(getWindow().getContext());
        this.progressDialog.setMessage(getString(R.string.geral_loading));

        if (!ConnectivityUtil.isAppConnectedToInternet(getBaseContext())){
            Util.showWarning(getWindow().getContext(), R.string.warning_no_connectivity);
        }

        if (savedInstanceState != null) {
            savedLocationLatLng = savedInstanceState.getParcelable(EXTRA_LOCATION);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (currentDisplayedMarker != null)
            outState.putParcelable(EXTRA_LOCATION, currentDisplayedMarker.getPosition());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);
        mMap.setOnMarkerClickListener(this);

        if (savedLocationLatLng != null) {
            handleShowMarker(savedLocationLatLng);
            savedLocationLatLng = null;
        }
    }

    @OnClick(R.id.search_cities_weather_btn)
    void searchSurroundingsBtnTapped(){
        if (!ConnectivityUtil.isAppConnectedToInternet(getBaseContext())){
            Util.showWarning(getWindow().getContext(), R.string.warning_no_connectivity);
            return;
        }

        showLoading();
        this.mPresenter.searchSurroundingCities(currentDisplayedMarker.getPosition());
    }

    @Override
    public void onCitiesLoaded(List<CityWeather> cities) {
        dismissLoading();

        Intent it = new Intent(getBaseContext(), ListCityWeatherActivity.class);
        it.putParcelableArrayListExtra(ListCityWeatherActivity.EXTRA_CITIES_ARRAY, (ArrayList<CityWeather>) cities);
        startActivity(it);
    }

    @Override
    public void onCitiesLoadError() {
        dismissLoading();

        if (!ConnectivityUtil.isAppConnectedToInternet(getBaseContext())){
            Util.showWarning(getWindow().getContext(), R.string.warning_no_connectivity);
        } else {
            Util.showWarning(getWindow().getContext(), R.string.warning_unknow_error);
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        removeMarker(this.currentDisplayedMarker);

        handleShowMarker(latLng);
    }

    private void handleShowMarker(LatLng latLng) {
        mSearchSurroundingsBtn.setVisibility(View.VISIBLE);

        currentDisplayedMarker = mMap.addMarker(new MarkerOptions().position(latLng));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        removeMarker(marker);
        mSearchSurroundingsBtn.setVisibility(View.GONE);
        return true;
    }

    private void removeMarker(Marker marker){
        if (marker != null) {
            marker.remove();
            this.currentDisplayedMarker = null;
        }
    }

    private void showLoading(){
        this.progressDialog.show();
    }

    private void dismissLoading(){
        if (this.progressDialog.isShowing()){
            this.progressDialog.dismiss();
        }
    }
}
