package com.josevi.countryinfo.activities;

import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.internal.LinkedTreeMap;
import com.josevi.countryinfo.R;
import com.josevi.countryinfo.api.CountryInfoRestClient;
import com.josevi.countryinfo.async.DownloadFlagImage;
import com.josevi.countryinfo.async.GetCountryFromDatabase;
import com.josevi.countryinfo.async.InsertCountryInDatabase;
import com.josevi.countryinfo.fragment.MapFragment;
import com.josevi.countryinfo.model.Country;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.josevi.countryinfo.utils.Constantes.COUNTRY_FULL_NAME;
import static com.josevi.countryinfo.utils.Constantes.COUNTRY_LATITUDE;
import static com.josevi.countryinfo.utils.Constantes.COUNTRY_LONGITUDE;
import static com.josevi.countryinfo.utils.Constantes.COUNTRY_NAME;

public class CountryInfoActivity extends AppCompatActivity {

    private AppCompatImageView flagImage;
    private TextView nameText, nativeNameText, regionText, capitalText, languagesText, translationsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_info);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        this.flagImage = findViewById(R.id.country_info_flag_image);

        this.nameText = findViewById(R.id.country_info_name_text);
        this.nativeNameText = findViewById(R.id.country_info_native_name_text);
        this.regionText = findViewById(R.id.country_info_region_text);
        this.capitalText = findViewById(R.id.country_info_capital_text);
        this.languagesText = findViewById(R.id.country_info_languages_text);
        this.translationsText = findViewById(R.id.country_info_translation_text);

        if (getIntent() != null && getIntent().getStringExtra(COUNTRY_FULL_NAME) != null
                && !getIntent().getStringExtra(COUNTRY_FULL_NAME).isEmpty()) {
            final String countryFullName = getIntent().getStringExtra(COUNTRY_FULL_NAME);

            new GetCountryFromDatabase(this).execute(countryFullName);
        }
    }

    public void setCountryInfo(Country country) {
        if (country.getName() != null) {
            nameText.setText(country.getName());
            nativeNameText.setText(country.getNativeName());
            regionText.setText(country.getRegion());
            capitalText.setText(country.getCapital());
            languagesText.setText(country.getLanguages());
            translationsText.setText(country.getTranslations());
            new DownloadFlagImage(flagImage).execute(country.getFlagUrl());

            final MapFragment mapFragment = new MapFragment();
            Bundle country_args = new Bundle();
            country_args.putDouble(COUNTRY_LONGITUDE, country.getLongitude());
            country_args.putDouble(COUNTRY_LATITUDE, country.getLatitude());
            country_args.putString(COUNTRY_NAME, country.getName());
            mapFragment.setArguments(country_args);

            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.country_info_map_adapter, mapFragment, "map");
            ft.commitAllowingStateLoss();
        }
    }

    public void callForANewCountry(final String countryFullName) {
        Call<Object> registerCall = CountryInfoRestClient.get().getCountriesByFullName(countryFullName);
        registerCall.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.isSuccessful()) {
                    System.out.println(countryFullName + "(full): OK");
                    if (response.body() != null) {
                        final Country country = new Country(((List<LinkedTreeMap>) response.body()).get(0));
                        new InsertCountryInDatabase(CountryInfoActivity.this).execute(country);
                        setCountryInfo(country);
                    }
                } else {
                    System.out.println(countryFullName + "(full): NOT OK");
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                System.out.println(countryFullName + "(full): FAIL");
            }

        });
    }
}
