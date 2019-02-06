package fi.kempula.quakeradar.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FeatureCollection {

    @SerializedName("features") ArrayList<Earthquake> earthquakes;

    public ArrayList<Earthquake> getEarthquakes() {
        return earthquakes;
    }

    public void setEarthquakes(ArrayList<Earthquake> earthquakes) {
        this.earthquakes = earthquakes;
    }

}
