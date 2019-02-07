package fi.kempula.quakeradar;

import android.app.Application;

import fi.kempula.quakeradar.core.module.ApiManagerModule;
import fi.kempula.quakeradar.network.api.ApiManager;

public class QuakeRadarApplication extends Application {

    private static QuakeRadarApplication instance;
    private ApiManager apiManager;


    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        initApiManager();
    }
    public static QuakeRadarApplication getInstance() {
        return instance;
    }

    private void initApiManager() {
        apiManager = ApiManagerModule.provideApiManager();
    }

    public ApiManager getApiManager() {
        return apiManager;
    }

    public void setApiManager(ApiManager apiManager) {
        this.apiManager = apiManager;
    }
}
