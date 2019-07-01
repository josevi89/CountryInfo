package com.josevi.countryinfo.database;

import com.josevi.countryinfo.dao.CountryDAO;
import com.josevi.countryinfo.model.Country;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Country.class}, version = 1)
public abstract class CountryDatabase extends RoomDatabase {
    public abstract CountryDAO countryDAO();
}