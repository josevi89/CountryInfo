package com.josevi.countryinfo.dao;

import com.josevi.countryinfo.model.Country;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface CountryDAO {

    @Query("SELECT * FROM Country WHERE name = :name")
    Country getCountryByName(String name);

    @Insert
    void insert(Country country);


}
