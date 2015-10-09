package com.example.sufian.livelocal;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.ic_home_tab,
            R.drawable.ic_discover_tab,
            R.drawable.ic_track_tab,
            R.drawable.ic_communicate_tab,
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }

    private void setupTabIcons() {

        TextView Home = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        Home.setText("Home");
        Home.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_home_tab, 0, 0);
        tabLayout.getTabAt(0).setCustomView(Home);

        TextView Discover = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        Discover.setText("Discover");
        Discover.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_discover_tab, 0, 0);
        tabLayout.getTabAt(1).setCustomView(Discover);

        TextView Track = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        Track.setText("Track");
        Track.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_track_tab, 0, 0);
        tabLayout.getTabAt(2).setCustomView(Track);

        TextView Communicate = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        Communicate.setText("Communicate");
        Communicate.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_communicate_tab, 0, 0);
        tabLayout.getTabAt(3).setCustomView(Communicate);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment(), "Home");
        adapter.addFragment(new DiscoverFragment(), "Discover");
        adapter.addFragment(new TrackFragment(), "Track");
        adapter.addFragment(new CommunicateFragment(), "Communicate");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
