package com.example.sufian.livelocal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Salman on 2/23/2016.
 */
public class EstablishmentsListAdapter extends ArrayAdapter<Establishment> {
    public EstablishmentsListAdapter(Context context, List<Establishment> establishments){
        super(context, R.layout.establishments_item, establishments);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Establishment estab = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.establishments_item, parent, false);
        }

        // Lookup view for data population
        TextView esName = (TextView) convertView.findViewById(R.id.establishmentName);
        TextView esStreet = (TextView) convertView.findViewById(R.id.establishmentStreet);
        TextView esCityState = (TextView) convertView.findViewById(R.id.establishmentCityState);
        TextView esPhone = (TextView) convertView.findViewById(R.id.establishmentPhone);
        TextView esSchedule = (TextView) convertView.findViewById(R.id.establishmentSchedule);

        // Populate the data into the template view using the data object
        esName.setText(estab.name);
        esStreet.setText(estab.street);
        esCityState.setText(estab.cityState);
        esPhone.setText(emptyIfNull(estab.phone));
        esSchedule.setText(estab.schedule);

        // Return the completed view to render on screen
        return convertView;
    }

    private String emptyIfNull( String content ){
        if( "null" == content  ){
            return "";
        }
        return content;
    }
}

class Establishment{
    String name, street, cityState, phone, schedule;
    JSONObject meta;
    public Establishment( String esName, String esStreet, String esCityState, String esPhone, String esSchedule, JSONObject esMeta ){
        this.name = esName;
        this.street = esStreet;
        this.cityState = esCityState;
        this.phone = esPhone;
        this.schedule = esSchedule;
        this.meta = esMeta;
    }
}