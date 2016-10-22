package com.teste.rodrigo.inlocoweather.ui;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.MenuItem;
import android.widget.TextView;

import com.teste.rodrigo.inlocoweather.R;
import com.teste.rodrigo.inlocoweather.model.CityWeather;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CityWeatherDetailActivity extends AppCompatActivity {

    public static final String EXTRA_CITY = "A@*O&YCL@";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.max_temp_tv)
    TextView maxTempTv;

    @BindView(R.id.min_temp_tv)
    TextView minTempTv;

    @BindView(R.id.description_temp_tv)
    TextView descriptionTempTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_city_weather);
        ButterKnife.bind(this);

        CityWeather city = getIntent().getParcelableExtra(EXTRA_CITY);

        mToolbar.setTitle(getString(R.string.title_activity_detail_city_weather, city.getCityName()));
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        setSupportActionBar(mToolbar);

        maxTempTv.setText           ( setSpannableText(R.string.max_temp, String.valueOf(city.getMaximumTemperature()) ));
        minTempTv.setText           ( setSpannableText(R.string.min_temp, String.valueOf(city.getMinimumTemperature()) ));
        descriptionTempTv.setText   ( setSpannableText(R.string.description_temp, city.getWeatherDescription() ));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return false;
    }

    private Spannable setSpannableText(int templateTextId, String placeHolderText){
        String fullText = getString(templateTextId, placeHolderText);

        Spannable spannable = new SpannableStringBuilder(fullText);
        StyleSpan boldSpan = new StyleSpan( Typeface.BOLD );
        spannable.setSpan( boldSpan, fullText.indexOf(placeHolderText), fullText.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE );

        return spannable;
    }

}
