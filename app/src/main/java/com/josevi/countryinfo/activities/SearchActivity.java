package com.josevi.countryinfo.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.internal.LinkedTreeMap;
import com.josevi.countryinfo.R;
import com.josevi.countryinfo.adapters.CountryAdapter;
import com.josevi.countryinfo.api.CountryInfoRestClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.josevi.countryinfo.utils.Constantes.COUNTRY_NAME;

public class SearchActivity extends AppCompatActivity {

    private EditText searchText;
    private TextView cancelButton;

    private RecyclerView resultsView;
    private SwipeRefreshLayout resultsRefreshContainer;
    private CountryAdapter countryAdapter;

    private Call<Object> registerCall = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        this.searchText = findViewById(R.id.search_search_text);
        this.cancelButton = findViewById(R.id.search_search_cancel_button);

        this.resultsView = findViewById(R.id.search_results_view);
        this.resultsView.setLayoutManager(new LinearLayoutManager(this));
        this.countryAdapter = new CountryAdapter(this, new ArrayList<String>());
        this.resultsView.setAdapter(this.countryAdapter);
        this.resultsRefreshContainer = findViewById(R.id.search_search_refresh_container);
        resultsView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int topRowVerticalPosition =
                        (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
                resultsRefreshContainer.setEnabled(topRowVerticalPosition >= 0);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        this.searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                final String nameToSearch = s.toString().toLowerCase();
                if (registerCall != null)
                    registerCall.cancel();
                registerCall = CountryInfoRestClient.get().getCountriesByName(nameToSearch);
                registerCall.enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                List<LinkedTreeMap> countryList = (List<LinkedTreeMap>) response.body();
                                List<String> countryNames = new ArrayList<>();
                                for (LinkedTreeMap country: countryList) {
                                    countryNames.add(country.get(COUNTRY_NAME).toString());
                                }
                                Collections.sort(countryNames);
                                SearchActivity.this.countryAdapter.setCountries(countryNames);

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {
                    }

                });
            }
        });

        this.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity.this.searchText.setText("");
                SearchActivity.this.countryAdapter.setCountries(new ArrayList<String>());
            }
        });
    }
}
