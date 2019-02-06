package fi.kempula.quakeradar.network.api;

import fi.kempula.quakeradar.model.FeatureCollection;
import io.reactivex.Observable;
import retrofit2.http.GET;

public interface EarthquakeAPIService {

    @GET("summary/all_day.geojson") Observable<FeatureCollection> getAllEarthquakes();

}
