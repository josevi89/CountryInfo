package com.josevi.countryinfo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.josevi.countryinfo.R;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import static com.josevi.countryinfo.utils.Constantes.COUNTRY_LATITUDE;
import static com.josevi.countryinfo.utils.Constantes.COUNTRY_LONGITUDE;
import static com.josevi.countryinfo.utils.Constantes.COUNTRY_NAME;

public class MapFragment extends Fragment {

    double longitude = 0, latitude = 0;
    String name = "";

    private SupportMapFragment mSupportMapFragment;

    public MapFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        if(getArguments().containsKey(COUNTRY_LONGITUDE))
            this.longitude = getArguments().getDouble(COUNTRY_LONGITUDE);
        if(getArguments().containsKey(COUNTRY_LATITUDE))
            this.latitude = getArguments().getDouble(COUNTRY_LATITUDE);
        if(getArguments().containsKey(COUNTRY_NAME))
            this.name = getArguments().getString(COUNTRY_NAME);

        mSupportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        if (mSupportMapFragment == null) {
            FragmentManager fragmentManager = getChildFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            mSupportMapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map);
            fragmentTransaction.replace(R.id.map, mSupportMapFragment).commit();
        }

        if (mSupportMapFragment != null) {
            mSupportMapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap map) {
                    if (map != null) {
                        if (latitude != 0 || longitude != 0) {
                            LatLng latLng = new LatLng(latitude, longitude);
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5));
                            map.addMarker(new MarkerOptions().position(latLng)).setTitle(name);
                        }
                    }
                }
            });
        };
        return rootView;
    }
}