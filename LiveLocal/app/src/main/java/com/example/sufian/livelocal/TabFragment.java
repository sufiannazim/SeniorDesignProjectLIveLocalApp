package com.example.sufian.livelocal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TabFragment extends Fragment {

    private Toolbar toolbar;

    private int[] tabIcons = {
            R.drawable.ic_home_tab,
            R.drawable.ic_discover_tab,
            R.drawable.ic_track_tab,
            R.drawable.ic_communicate_tab,
    };

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 4 ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View x =  inflater.inflate(R.layout.tab_layout,null);
        tabLayout = (TabLayout) x.findViewById(R.id.tabs);
        viewPager = (ViewPager) x.findViewById(R.id.viewpager);
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
                setupTabLayout(tabLayout);
            }
        });
        return x;
    }

    public void setupTabLayout(TabLayout tabLayout) {

        TextView tab1 = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_tab, null);
        tab1.setText("Home");
        tab1.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_home_tab, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tab1);

        TextView tab2 = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_tab, null);
        tab2.setText("Discover");
        tab2.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_discover_tab, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tab2);

        TextView tab3 = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_tab, null);
        tab3.setText("Track");
        tab3.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_track_tab, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tab3);

        TextView tab4 = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_tab, null);
        tab4.setText("COMM");
        tab4.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_communicate_tab, 0, 0);
        tabLayout.getTabAt(3).setCustomView(tab4);

    }

    class MyAdapter extends FragmentPagerAdapter{

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            switch (position){
                case 0 : return new HomeFragment();
                case 1 : return new DiscoverFragment();
                case 2 : return new TrackFragment();
                case 3 : return new CommunicateFragment();
            }
            return null;
        }

        @Override
        public int getCount() {

            return int_items;

        }
        
        @Override
        public CharSequence getPageTitle(int position) {

            switch (position){
                case 0 :
                    return "Home";
                case 1 :
                    return "Discover";
                case 2 :
                    return "Track";
                case 3 :
                    return "Communicate";
            }
            return null;
        }
    }

}