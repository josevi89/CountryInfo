package com.josevi.countryinfo.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.josevi.countryinfo.R;
import com.josevi.countryinfo.activities.CountryInfoActivity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.josevi.countryinfo.utils.Constantes.COUNTRY_FULL_NAME;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> {

    private Context mContext;
    private List<String> countries;

    public CountryAdapter(Context mContext, List<String> countries) {
        this.mContext = mContext;
        this.countries = countries;
    }

    @NonNull
    @Override
    public CountryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_country, viewGroup, false);
        CountryAdapter.ViewHolder vh = new CountryAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CountryAdapter.ViewHolder holder, int position) {
        final String countryName = countries.get(position);
        holder.itemName.setText(countryName);

        holder.itemContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent countryInfoIntent = new Intent(mContext, CountryInfoActivity.class);
                countryInfoIntent.putExtra(COUNTRY_FULL_NAME, countryName);
                mContext.startActivity(countryInfoIntent);
            }
        });
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemName;
        public RelativeLayout itemContainer;

        public ViewHolder(View v) {
            super(v);
            itemContainer = v.findViewById(R.id.item_container);
            itemName = v.findViewById(R.id.item_name);
        }
    }
}
