package com.example.sufian.livelocal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DiscoverFragment extends Fragment {
    private List<String> menu;
    TextView STTtxtView;

    public DiscoverFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_discover, container, false);
        ListView lv = (ListView) rootView.findViewById(R.id.discoverListView);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = menu.get(position);
                
                /*if( item.equals("Events") ){
                    Intent intent = new Intent(getActivity(), EventActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), item, Toast.LENGTH_SHORT).show();
                }*/
                Intent intent;
                switch (item){
                    case "Events":
                        intent = new Intent(getActivity(), EventActivity.class);
                        startActivity(intent);
                        break;
                    case "Season's Top 10":
                        intent = new Intent(getActivity(), SeasonsTop10.class);
                        startActivity(intent);
                        break;
                    case "Trails":
                        Toast.makeText(getActivity(), item, Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        menu = new ArrayList<String>();
        menu.add("Season's Top 10");
        menu.add("Events");
        menu.add("Trails");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, menu);
        lv.setAdapter(adapter);

        return rootView;
    }

}
