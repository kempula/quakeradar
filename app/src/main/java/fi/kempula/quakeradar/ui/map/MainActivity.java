package fi.kempula.quakeradar.ui.map;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import fi.kempula.quakeradar.R;
import fi.kempula.quakeradar.core.module.ApiManagerModule;
import fi.kempula.quakeradar.model.Earthquake;
import fi.kempula.quakeradar.network.api.ApiManager;
import fi.kempula.quakeradar.ui.map.fragment.EarthquakeListFragment;
import fi.kempula.quakeradar.ui.map.fragment.MapFragment;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends FragmentActivity {

    private ArrayList<Earthquake> list = new ArrayList<>();
    private CompositeDisposable disposables = new CompositeDisposable();
    private MapFragment mapFragment = null;
    private EarthquakeListFragment listFragment = null;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_map:
                    if(mapFragment == null) {
                        mapFragment = MapFragment.newInstance(list);
                    }
                    switchFragment(mapFragment);
                    return true;
                case R.id.navigation_list:
                    if(listFragment == null) {
                        listFragment = EarthquakeListFragment.newInstance(list);
                    }
                    switchFragment(listFragment);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hideStatusBar();
        initializeMapFragment();
        setBottomNavigation();
        getEarthquakeData();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        hideStatusBar();
    }

    private void setBottomNavigation() {
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void getEarthquakeData() {
        ApiManager apiManager = ApiManagerModule.provideApiManager();

        Disposable disposable = apiManager.earthquakeList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(featureCollection -> {
                    populateList(featureCollection.getEarthquakes());
                    passDataToFragments(featureCollection.getEarthquakes());
                }, error -> {
                    error.printStackTrace();
                });

        disposables.add(disposable);
    }

    private void initializeMapFragment() {
        mapFragment = new MapFragment();
        switchFragment(mapFragment);
    }

    private void passDataToFragments(ArrayList<Earthquake> earthquakes) {
        if(mapFragment != null) {
            mapFragment.setEarthquakeArrayList(earthquakes);
        }

        if(listFragment != null) {
            listFragment.setEarthquakesInAdapter(earthquakes);
        }
    }

    private void populateList(ArrayList<Earthquake> earthquakes) {
        list = new ArrayList<>(earthquakes);
    }

    private void hideStatusBar() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    private void switchFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

}
