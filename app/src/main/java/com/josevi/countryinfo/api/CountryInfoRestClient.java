package com.josevi.countryinfo.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.josevi.countryinfo.utils.DateDeserializer;
import com.josevi.countryinfo.utils.DateSerializer;
import com.josevi.countryinfo.utils.DoubleSerializer;
import com.josevi.countryinfo.utils.IntegerSerializer;
import com.josevi.countryinfo.utils.StringSerializer;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CountryInfoRestClient {

    public static CountryInfoApi REST_CLIENT;

    private static String ROOT = "http://restcountries.eu/rest/v2/";

    static {
        setupRestClient();
    }

    public CountryInfoRestClient(){}

    private static void setupRestClient() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();

                String postBody = bodyToString(original.body());
                String newPostBody = URLDecoder.decode(postBody);
                RequestBody body = original.body();
                RequestBody requestBody = null;

                if(body!=null){
                    requestBody = RequestBody.create(original.body().contentType(),newPostBody);
                }

                Request request;
                request = original.newBuilder()
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }

            public String bodyToString(final RequestBody request){
                try {
                    final RequestBody copy = request;
                    final Buffer buffer = new Buffer();
                    if(copy != null)
                        copy.writeTo(buffer);
                    else
                        return "";
                    return buffer.readUtf8();
                }
                catch (final IOException e) {
                    return "did not work";
                }
            }
        });

        httpClient.addInterceptor(logging);

        OkHttpClient client = httpClient
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateDeserializer())
                .registerTypeAdapter(Date.class, new DateSerializer())
                .registerTypeAdapter(String.class, new StringSerializer())
                .registerTypeAdapter(Integer.class, new IntegerSerializer())
                .registerTypeAdapter(Double.class, new DoubleSerializer())
                .excludeFieldsWithoutExposeAnnotation()
                .serializeNulls()
                .setLenient().create();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ROOT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        REST_CLIENT = retrofit.create(CountryInfoApi.class);
    }

    public static CountryInfoApi get() {
        return REST_CLIENT;
    }
}
