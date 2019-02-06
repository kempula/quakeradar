package fi.kempula.quakeradar.core.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;

import fi.kempula.quakeradar.network.api.ApiManager;
import fi.kempula.quakeradar.network.api.EarthquakeAPIService;
import fi.kempula.quakeradar.network.util.DateDeserializer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManagerModule {

    public static ApiManager provideApiManager() {
        return new ApiManager(provideEarthquakeAPIService());
    }

    private static EarthquakeAPIService provideEarthquakeAPIService() {

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateDeserializer())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://earthquake.usgs.gov/earthquakes/feed/v1.0/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit.create(EarthquakeAPIService.class);
    }

}
