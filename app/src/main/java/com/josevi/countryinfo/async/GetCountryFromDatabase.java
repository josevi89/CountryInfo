package com.josevi.countryinfo.async;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;

import com.josevi.countryinfo.activities.CountryInfoActivity;
import com.josevi.countryinfo.database.CountryDatabase;
import com.josevi.countryinfo.model.Country;
import com.larvalabs.svgandroid.SVG;
import com.larvalabs.svgandroid.SVGParser;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import androidx.room.Room;

import static com.josevi.countryinfo.utils.Constantes.COUNTRIES_DATABASE_NAME;

public class GetCountryFromDatabase extends AsyncTask<String, Void, Country> {

    Activity mActivity;
    String countryName;
    CountryDatabase db;

    public GetCountryFromDatabase(Activity mActivity){
        this.mActivity = mActivity;
        db = Room.databaseBuilder(mActivity,
                CountryDatabase.class, COUNTRIES_DATABASE_NAME).build();
    }

    protected Country doInBackground(String... countryNames) {
        countryName = countryNames[0];

        Country country = db.countryDAO().getCountryByName(countryName);
        return country;
    }

    protected void onPostExecute(Country country) {
        if (country != null)
            ((CountryInfoActivity)mActivity).setCountryInfo(country);
        else
            ((CountryInfoActivity)mActivity).callForANewCountry(countryName);

    }
}
