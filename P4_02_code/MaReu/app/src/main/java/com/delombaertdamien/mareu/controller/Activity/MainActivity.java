package com.delombaertdamien.mareu.controller.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

import com.delombaertdamien.mareu.DI.DI;
import com.delombaertdamien.mareu.R;
import com.delombaertdamien.mareu.controller.Fragment.DetailFragment;
import com.delombaertdamien.mareu.controller.Fragment.MainFragment;
import com.delombaertdamien.mareu.model.Filter;
import com.delombaertdamien.mareu.service.MeetingApiService;
import com.delombaertdamien.mareu.service.PlaceCompany;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final MeetingApiService mApiService = DI.getMeetingApiService();
    private MainFragment mainFragment;
    private DetailFragment detailFragment;
    private final Filter filter = new Filter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mApiService.getListWithFilter(filter);

        configureAndShowMainFragment();
        configureAndShowDetailFragment();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        SubMenu menuItem = (menu.getItem(0).getSubMenu());

        List<String> listPlace = PlaceCompany.getPlaceAvailable();

        SubMenu itemSubMenuOption1 = (menuItem.findItem(R.id.option_1_filter)).getSubMenu();

        for (int i = 1; i < 25; i++) {
            int index = (i) - 1;
            itemSubMenuOption1.add(R.id.option_filter_1_group, Menu.NONE, Menu.NONE, i + "H");
            //filter.addHour(i);
            int finalI = i;
            itemSubMenuOption1.getItem(index).setOnMenuItemClickListener(item -> {
                setOnClickOnItemHour(item, finalI);
                return true;

            });
            itemSubMenuOption1.getItem(index).setCheckable(true).setChecked(true);

        }

        SubMenu itemSubMenuOption2 = (menuItem.findItem(R.id.option_2_filter)).getSubMenu();

        for (int i = 0; i < listPlace.size(); i++) {
            itemSubMenuOption2.add(R.id.option_filter_2_group, Menu.NONE, Menu.NONE, "Local " + listPlace.get(i));
            int finalI = i;
            itemSubMenuOption2.getItem(i).setOnMenuItemClickListener(item -> {
                setOnClickOnItemPlace(item, listPlace.get(finalI));
                return true;

            });
            itemSubMenuOption2.getItem(i).setCheckable(true).setChecked(true);
        }

        return true;
    }

    private void setOnClickOnItemHour(MenuItem item, int hour) {
        if (item.isChecked()) {
            item.setChecked(false);
            filter.addHour(hour);
        } else {
            filter.removeHour(hour);
            item.setChecked(true);
        }
        mApiService.getListWithFilter(filter);
        refreshFragmentMain();
        //item.setChecked(true);
    }

    private void setOnClickOnItemPlace(MenuItem item, String place) {
        if (item.isChecked()) {
            item.setChecked(false);
            filter.addPlace(place);
        } else {
            item.setChecked(true);
            filter.removePlace(place);
        }
        mApiService.getListWithFilter(filter);
        refreshFragmentMain();
        // item.setChecked(true);
    }

    private void configureAndShowMainFragment() {

        mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_activity_main);

        if (mainFragment == null) {
            mainFragment = new MainFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_layout_activity_main, mainFragment)
                    .commit();
        }
    }

    private void configureAndShowDetailFragment() {

        if (mApiService.getMeetingsToShow().size() > 0 && mApiService.getMeetingToDisplay() == null) {
            mApiService.setMeetingToDisplay(mApiService.getMeetingsToShow().get(mApiService.getMeetingsToShow().size() - 1));
        }

        detailFragment = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_activity_detail);

        if (detailFragment == null && findViewById(R.id.frame_layout_activity_detail) != null) {
            detailFragment = new DetailFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_layout_activity_detail, detailFragment)
                    .commit();
        }

    }

    public void refreshFragmentDetail() {

        detailFragment = new DetailFragment();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout_activity_detail, detailFragment)
                .commit();
    }
    private void refreshFragmentMain(){

        mainFragment.refreshUI();
    }

}
