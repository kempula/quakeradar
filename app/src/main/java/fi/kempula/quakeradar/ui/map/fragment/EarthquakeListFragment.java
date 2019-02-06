package fi.kempula.quakeradar.ui.map.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import fi.kempula.quakeradar.R;
import fi.kempula.quakeradar.model.Earthquake;
import fi.kempula.quakeradar.ui.map.adapter.EarthquakeListAdapter;

public class EarthquakeListFragment extends Fragment implements SortingDialog.SortingListener {

    private static final String EARTHQUAKE_LIST = "EARTHQUAKE_LIST";

    private EarthquakeListAdapter adapter;
    private ArrayList<Earthquake> originalEarthquakes = new ArrayList<>();

    private SortingDialog sortingDialog;
    private Boolean isSortedByMagnitude = false;

    public static EarthquakeListFragment newInstance(ArrayList<Earthquake> list) {
        EarthquakeListFragment fragment = new EarthquakeListFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(EARTHQUAKE_LIST, list);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(getArguments() != null) {
            ArrayList<Earthquake> arrayList = getArguments().getParcelableArrayList(EARTHQUAKE_LIST);
            if (arrayList != null) {
                originalEarthquakes = new ArrayList<>(arrayList);
            }
        }

        View view = inflater.inflate(R.layout.fragment_earthquake_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.earthquake_list);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new EarthquakeListAdapter(getContext(), originalEarthquakes);
        recyclerView.setAdapter(adapter);

        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(mDividerItemDecoration);

        setFAB(view);

        return view;
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        if(fragment instanceof SortingDialog) {
            SortingDialog sortingDialog = (SortingDialog) fragment;
            sortingDialog.setSortingListener(sortType -> { sortEarthquakes(sortType); });
        }
    }

    private void setFAB(View view) {
        FloatingActionButton fab = view.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortingDialog = SortingDialog.newInstance(isSortedByMagnitude);
                sortingDialog.setSortingListener(EarthquakeListFragment.this);
                sortingDialog.show(getActivity().getSupportFragmentManager(), "sort");
            }
        });

    }

    private void sortEarthquakes(SortingDialog.SortType sortType) {
        if(sortType == SortingDialog.SortType.MAGNITUDE) {
            isSortedByMagnitude = true;
            sortByMagnitude();
            return;
        }

        isSortedByMagnitude = false;
        sortByDate();
    }

    private void sortByDate() {
        sortingDialog.dismiss();
        adapter.setEarthquakes(originalEarthquakes);
    }

    private void sortByMagnitude() {
        sortingDialog.dismiss();

        ArrayList<Earthquake> sortedEarthquakes = new ArrayList<>(originalEarthquakes);

        Collections.sort(sortedEarthquakes, new Comparator<Earthquake>() {
            @Override
            public int compare(Earthquake earthquake, Earthquake t1) {
                return Double.compare(earthquake.getProperties().getMagnitude(), t1.getProperties().getMagnitude());

            }
        });

        Collections.reverse(sortedEarthquakes);
        adapter.setEarthquakes(sortedEarthquakes);
    }

    public void setEarthquakesInAdapter(ArrayList<Earthquake> earthquakes) {
        adapter.setEarthquakes(earthquakes);
    }

    @Override
    public void onSort(SortingDialog.SortType sortType) {
        sortEarthquakes(sortType);
    }
}
