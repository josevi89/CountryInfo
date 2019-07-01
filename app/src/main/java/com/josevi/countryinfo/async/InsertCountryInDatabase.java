package com.josevi.countryinfo.async;

import android.app.Activity;
import android.os.AsyncTask;

import com.josevi.countryinfo.activities.CountryInfoActivity;
import com.josevi.countryinfo.database.CountryDatabase;
import com.josevi.countryinfo.model.Country;

import androidx.room.Room;

import static com.josevi.countryinfo.utils.Constantes.COUNTRIES_DATABASE_NAME;

public class InsertCountryInDatabase extends AsyncTask<Country, Void, Country> {

    Activity mActivity;
    CountryDatabase db;

    public InsertCountryInDatabase(Activity mActivity){
        this.mActivity = mActivity;
        db = Room.databaseBuilder(mActivity,
                CountryDatabase.class, COUNTRIES_DATABASE_NAME).build();
    }

    protected Country doInBackground(Country... countryNames) {
        Country country = countryNames[0];
        db.countryDAO().insert(country);
        return country;
    }

    protected void onPostExecute(Country country) {
        if (country != null)
            ((CountryInfoActivity)mActivity).setCountryInfo(country);
    }
}
