package fi.kempula.quakeradar.ui.map.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import fi.kempula.quakeradar.R;
import fi.kempula.quakeradar.model.Earthquake;
import fi.kempula.quakeradar.model.Geometry;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private static final String EARTHQUAKE_LIST = "EARTHQUAKE_LIST";

    MapView mapView;
    private GoogleMap googleMap;
    private ArrayList<Earthquake> earthquakeArrayList = new ArrayList<>();

    public static MapFragment newInstance(ArrayList<Earthquake> list) {
        MapFragment fragment = new MapFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(EARTHQUAKE_LIST, list);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {

        if(getArguments() != null) {
            ArrayList<Earthquake> arrayList = getArguments().getParcelableArrayList(EARTHQUAKE_LIST);
            if (arrayList != null) {
                earthquakeArrayList = new ArrayList<>(arrayList);
            }
        }

        View view = layoutInflater.inflate(R.layout.fragment_map, viewGroup, false);

        mapView = view.findViewById(R.id.map);
        mapView.onCreate(bundle);
        mapView.onResume();
        mapView.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        populateMapWithMarkers();
    }

    private void populateMapWithMarkers() {
        googleMap.clear();

        if(earthquakeArrayList.size() == 0) {
            return;
        }
        for(int i = 0; i < earthquakeArrayList.size(); i++) {
            Geometry geometry = earthquakeArrayList.get(i).getGeometry();

            LatLng latLng = new LatLng(geometry.getCoordinates().get(1), geometry.getCoordinates().get(0));
            googleMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(earthquakeArrayList.get(i).getProperties().getTitle()));
        }
    }

    public void setEarthquakeArrayList(ArrayList<Earthquake> earthquakeArrayList) {
        this.earthquakeArrayList = new ArrayList<>(earthquakeArrayList);
        populateMapWithMarkers();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
