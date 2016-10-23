package com.teste.rodrigo.inlocoweather.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.teste.rodrigo.inlocoweather.R;
import com.teste.rodrigo.inlocoweather.adapter.SimpleSingleLineAdapter;
import com.teste.rodrigo.inlocoweather.decorator.DividerItemDecorator;
import com.teste.rodrigo.inlocoweather.model.CityWeather;
import com.teste.rodrigo.inlocoweather.model.GeocodedAddress;
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
    private static final String EXTRA_IS_SHOWING_MAP = "A)S(PDOxhiA";
    private static final String EXTRA_LOADED_OPTIONS = "OULFYAV>@YH";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.search_cities_weather_btn)
    Button mSearchSurroundingsBtn;

    @BindView(R.id.search_options_list)
    RecyclerView mSearchOptionsList;

    @BindView(R.id.map_container)
    View mapContainer;

    private SearchView searchView;

    private ISearchCitiesPresenter mPresenter;
    private GoogleMap mMap;
    private Marker currentDisplayedMarker;

    private ProgressDialog progressDialog;

    private LatLng savedLocationLatLng = null;
    private InputMethodManager imm = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_city_map);
        ButterKnife.bind(this);

        mToolbar.setTitle(R.string.title_activity_search_city_map);
        setSupportActionBar(mToolbar);

        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        this.mSearchOptionsList.addItemDecoration(new DividerItemDecorator(getBaseContext()));
        this.mPresenter = new SearchCitiesPresenterImp(this, getBaseContext());
        this.progressDialog = new ProgressDialog(getWindow().getContext());
        this.progressDialog.setMessage(getString(R.string.geral_loading));

        if (!ConnectivityUtil.isAppConnectedToInternet(getBaseContext())){
            Util.showWarning(getWindow().getContext(), R.string.warning_no_connectivity);
        }

        if (savedInstanceState != null) {
            savedLocationLatLng = savedInstanceState.getParcelable(EXTRA_LOCATION);
            boolean isShowingMap = savedInstanceState.getBoolean(EXTRA_IS_SHOWING_MAP);
            if (isShowingMap){
                showMapContainer();
            } else {
                showCitiesOptionsList();
            }

            ArrayList<GeocodedAddress> options = savedInstanceState.getParcelableArrayList(EXTRA_LOADED_OPTIONS);
            if (options != null) {
                initOptionsListAdapter(options);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (currentDisplayedMarker != null)
            outState.putParcelable(EXTRA_LOCATION, currentDisplayedMarker.getPosition());

        if (mapContainer.getVisibility() == View.VISIBLE)
            outState.putBoolean(EXTRA_IS_SHOWING_MAP, true);

        if (mSearchOptionsList.getAdapter() != null) {
            SimpleSingleLineAdapter<GeocodedAddress> adapter = (SimpleSingleLineAdapter<GeocodedAddress>) mSearchOptionsList.getAdapter();
            outState.putParcelableArrayList(EXTRA_LOADED_OPTIONS, (ArrayList<GeocodedAddress>) adapter.getDataList());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.search);

        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint(getString(R.string.geral_search_city_to_pin));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText == null || newText.isEmpty()){
                    showMapContainer();
                }
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!ConnectivityUtil.isAppConnectedToInternet(getBaseContext())) {
                    Util.showWarning(getWindow().getContext(), R.string.warning_no_connectivity);
                    return true;
                }

                showLoading();
                showCitiesOptionsList();
                mPresenter.searchCityByTerm(query);

                return true;
            }

        });

        return true;
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
    public void onCitiesLoadedByTerm(List<GeocodedAddress> addresses) {
        dismissLoading();
        initOptionsListAdapter(addresses);
    }

    private void initOptionsListAdapter(List<GeocodedAddress> addresses) {
        if (mSearchOptionsList.getAdapter() != null) {
            SimpleSingleLineAdapter<GeocodedAddress> adapter = (SimpleSingleLineAdapter<GeocodedAddress>) mSearchOptionsList.getAdapter();
            adapter.updateAllData(addresses);
        } else {
            SimpleSingleLineAdapter<GeocodedAddress> adapter = new SimpleSingleLineAdapter<>(addresses);
            adapter.setOnItemClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GeocodedAddress address = (GeocodedAddress) v.getTag();
                    onMapClick(new LatLng(address.getLatitude(), address.getLongitude()));//Mock Click - to force remove previous marker

                    if (searchView != null)
                        imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);

                    showMapContainer();
                }
            });

            mSearchOptionsList.setAdapter(adapter);
        }
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
        mSearchSurroundingsBtn.animate()
                .alpha(1f)
                .setDuration(250)
                .setListener(null);


        currentDisplayedMarker = mMap.addMarker(new MarkerOptions().position(latLng));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    private void showCitiesOptionsList() {
        mapContainer.setVisibility(View.GONE);
        mSearchOptionsList.setVisibility(View.VISIBLE);
    }

    private void showMapContainer() {
        mapContainer.setVisibility(View.VISIBLE);
        mSearchOptionsList.setVisibility(View.GONE);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        removeMarker(marker);
        mSearchSurroundingsBtn.animate()
                .alpha(0f)
                .setDuration(250)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mSearchSurroundingsBtn.setVisibility(View.GONE);
                    }
                });

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
