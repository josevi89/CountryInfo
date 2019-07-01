package com.josevi.countryinfo.api;

import com.josevi.countryinfo.utils.Constantes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CountryInfoApi {

    @GET(Constantes.URL_NAME + "/{name}")
    Call<Object> getCountriesByName(
            @Path("name") String name);

    @GET(Constantes.URL_NAME + "/{name}" +"?fullText=true")
    Call<Object> getCountriesByFullName(
            @Path("name") String name);

}
