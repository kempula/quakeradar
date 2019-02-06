package fi.kempula.quakeradar.ui.map.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import fi.kempula.quakeradar.R;
import fi.kempula.quakeradar.model.Earthquake;
import fi.kempula.quakeradar.model.Properties;

public class EarthquakeListAdapter extends RecyclerView.Adapter<EarthquakeListAdapter.EarthquakeViewHolder> {

    private final LayoutInflater inflater;
    private List<Earthquake> earthquakes;

    public EarthquakeListAdapter(Context context, List<Earthquake> list) {
        inflater = LayoutInflater.from(context);
        earthquakes = new ArrayList<>();
        earthquakes.addAll(list);
    }

    public void setEarthquakes(ArrayList<Earthquake> list) {
        this.earthquakes.clear();
        this.earthquakes.addAll(list);
        notifyDataSetChanged();
    }

    public class EarthquakeViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView timestamp;
        private TextView magnitude;
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);

        public EarthquakeViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            timestamp = itemView.findViewById(R.id.timestamp);
            magnitude = itemView.findViewById(R.id.magnitude);
        }

        public void setItem(Earthquake earthquake) {

            Properties properties = earthquake.getProperties();
            title.setText(properties.getPlace());
            timestamp.setText(dateFormat.format(properties.getTime()));

            NumberFormat formatter = new DecimalFormat("#0.00");
            String magnitudeText = formatter.format(properties.getMagnitude()) + " M";
            this.magnitude.setText(magnitudeText);

        }
    }

    @NonNull
    @Override
    public EarthquakeListAdapter.EarthquakeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = inflater.inflate(R.layout.row_earthquake, viewGroup, false);
        return new EarthquakeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EarthquakeListAdapter.EarthquakeViewHolder viewHolder, int i) {
        Earthquake earthquake = earthquakes.get(i);
        viewHolder.setItem(earthquake);
    }

    @Override
    public int getItemCount() {
        return earthquakes.size();
    }
}
