package com.josevi.countryinfo.async;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;

import com.larvalabs.svgandroid.SVG;
import com.larvalabs.svgandroid.SVGParser;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadFlagImage extends AsyncTask<String, Void, Drawable> {
    ImageView flagImage;

    public DownloadFlagImage(ImageView flagImage) {
        this.flagImage = flagImage;
    }

    protected Drawable doInBackground(String... urls) {
        Drawable flagDrawable = null;
        try {
            URL flagUrl = new URL(urls[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) flagUrl.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            SVG svg = SVGParser.getSVGFromInputStream(inputStream);
            flagDrawable = svg.createPictureDrawable();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flagDrawable;
    }

    protected void onPostExecute(Drawable flagDrawable) {
        if (flagDrawable != null) {
            flagImage.setImageDrawable(flagDrawable);
            flagImage.setVisibility(View.VISIBLE);
        }
        else
            flagImage.setVisibility(View.INVISIBLE);
    }
}
