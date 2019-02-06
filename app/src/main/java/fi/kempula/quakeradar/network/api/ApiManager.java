package fi.kempula.quakeradar.network.api;
import fi.kempula.quakeradar.model.FeatureCollection;
import io.reactivex.Observable;

public class ApiManager {

    private final EarthquakeAPIService earthquakeAPIService;

    public ApiManager(EarthquakeAPIService earthquakeAPIService) {
        this.earthquakeAPIService = earthquakeAPIService;
    }

    public Observable<FeatureCollection> earthquakeList() {
        return earthquakeAPIService.getAllEarthquakes();
    }
}
