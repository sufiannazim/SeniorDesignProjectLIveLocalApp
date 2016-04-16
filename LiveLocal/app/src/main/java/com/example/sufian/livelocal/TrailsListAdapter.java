package com.example.sufian.livelocal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Salman on 12/9/2015.
 */
public class TrailsListAdapter extends ArrayAdapter<Trail> {
    public TrailsListAdapter(Context context, List<Trail> trails){
        super(context, R.layout.trail_item, trails);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Trail trail = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.trail_item, parent, false);
        }
        // Lookup view for data population
        TextView trailName = (TextView) convertView.findViewById(R.id.trailName);
        TextView trailRegion = (TextView) convertView.findViewById(R.id.trailRegion);
        TextView trailCategories = (TextView) convertView.findViewById(R.id.trailCategories);
        TextView trailSeasons = (TextView) convertView.findViewById(R.id.trailSeasons);
        // Populate the data into the template view using the data object
        trailName.setText(trail.name);
        trailRegion.setText(trail.region);

        if( !(trail.categories.isEmpty()) ){
            StringBuilder catBuilder = new StringBuilder();
            catBuilder.append("Categories: ");
            for ( int i=1; i<trail.categories.size(); i++ ){
                catBuilder.append( trail.categories.get(i) + " " );
            }
            trailCategories.setText(catBuilder.toString());
        }

        if( !(trail.seasons.isEmpty()) ) {
            StringBuilder seasonBuilder = new StringBuilder();
            seasonBuilder.append("Seasons: ");
            for (int i = 1; i < trail.seasons.size(); i++) {
                seasonBuilder.append(trail.seasons.get(i) + " ");
            }
            trailSeasons.setText(seasonBuilder.toString());
        }
        // Return the completed view to render on screen
        return convertView;
    }
}

class Trail{
    String name;
    String region;
    ArrayList<String> categories;
    ArrayList<String> seasons;
    JSONArray establishments;

    public Trail( String trailName, String trailRegion, ArrayList<String> trailCategories, ArrayList<String> trailSeasons, JSONArray trailEstablishments ){
        this.name = trailName;
        this.region = trailRegion;
        this.categories = trailCategories;
        this.seasons = trailSeasons;
        this.establishments = trailEstablishments;
    }
}
