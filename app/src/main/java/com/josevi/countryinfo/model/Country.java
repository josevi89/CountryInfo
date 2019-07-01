package com.josevi.countryinfo.model;

import com.google.gson.internal.LinkedTreeMap;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import static com.josevi.countryinfo.utils.Constantes.COUNTRY_CAPITAL;
import static com.josevi.countryinfo.utils.Constantes.COUNTRY_FLAG;
import static com.josevi.countryinfo.utils.Constantes.COUNTRY_LANGUAGES;
import static com.josevi.countryinfo.utils.Constantes.COUNTRY_LAT_LNG;
import static com.josevi.countryinfo.utils.Constantes.COUNTRY_NAME;
import static com.josevi.countryinfo.utils.Constantes.COUNTRY_NATIVE_NAME;
import static com.josevi.countryinfo.utils.Constantes.COUNTRY_REGION;
import static com.josevi.countryinfo.utils.Constantes.COUNTRY_TRANSLATIONS;
import static com.josevi.countryinfo.utils.Constantes.LAGUAGE_NAME;
import static com.josevi.countryinfo.utils.Constantes.TRANSLATION_DE;

@Entity
public class Country {

    @PrimaryKey
    @NonNull
    private String name;
    @ColumnInfo
    private String nativeName;
    @ColumnInfo
    private String region;
    @ColumnInfo
    private String capital;
    @ColumnInfo
    private String languages;
    @ColumnInfo
    private String translations;
    @ColumnInfo
    private String flagUrl;
    @ColumnInfo
    private double latitude;
    @ColumnInfo
    private double longitude;

    public Country(){}

    public Country(LinkedTreeMap response) {
        this.name = response.get(COUNTRY_NAME).toString();
        this.nativeName = response.get(COUNTRY_NATIVE_NAME).toString();
        this.region = response.get(COUNTRY_REGION).toString();
        this.capital = response.get(COUNTRY_CAPITAL).toString();
        List<LinkedTreeMap> languagesList = (List<LinkedTreeMap>) response.get(COUNTRY_LANGUAGES);
        if (languagesList != null && !languagesList.isEmpty()) {
            String languages = "";
            for (LinkedTreeMap language: languagesList) {
                languages += language.get(LAGUAGE_NAME).toString() +", ";
            }
            this.languages = languages.substring(0, languages.length() - 2);
        }
        else
            this.languages = "";
        LinkedTreeMap translationsList = (LinkedTreeMap) response.get(COUNTRY_TRANSLATIONS);
        this.translations = translationsList.get(TRANSLATION_DE).toString();
        this.flagUrl = response.get(COUNTRY_FLAG).toString();
        List<Double> latlng = (List<Double>) response.get(COUNTRY_LAT_LNG);
        this.latitude = latlng.get(0);
        this.longitude = latlng.get(1);
    }

    public String getName() {
        return name;
    }

    public String getNativeName() {
        return nativeName;
    }

    public String getRegion() {
        return region;
    }

    public String getCapital() {
        return capital;
    }

    public String getLanguages() {
        return languages;
    }

    public String getTranslations() {
        return translations;
    }

    public String getFlagUrl() {
        return flagUrl;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNativeName(String nativeName) {
        this.nativeName = nativeName;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public void setTranslations(String translations) {
        this.translations = translations;
    }

    public void setFlagUrl(String flagUrl) {
        this.flagUrl = flagUrl;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
