package com.example.sufian.livelocal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class EventListAdapter extends ArrayAdapter<Event> {
    public EventListAdapter(Context context, List<Event> events){
        super(context, R.layout.event_item, events);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Event event = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_item, parent, false);
        }
        // Lookup view for data population
        TextView evName = (TextView) convertView.findViewById(R.id.evName);
        TextView evDate = (TextView) convertView.findViewById(R.id.evDate);
        // Populate the data into the template view using the data object
        evName.setText(event.name);
        evDate.setText(event.date);
        // Return the completed view to render on screen
        return convertView;
    }
}

class Event{
    public final String name;
    public final String date;

    public Event( String evName, String evDate ){
        this.name = evName;
        this.date = evDate;
    }
}