package com.delombaertdamien.mareu.controller.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.delombaertdamien.mareu.DI.DI;
import com.delombaertdamien.mareu.R;
import com.delombaertdamien.mareu.controller.Fragment.DetailFragment;
import com.delombaertdamien.mareu.controller.Fragment.MainFragment;
import com.delombaertdamien.mareu.service.MeetingApiService;

public class MainActivity extends AppCompatActivity {

    private final MeetingApiService mApiService = DI.getMeetingApiService();
    private MainFragment mainFragment;
    private DetailFragment detailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureAndShowMainFragment();
        configureAndShowDetailFragment();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }
    public void onOptionFilterListItemClick (MenuItem item){

        switch (item.getItemId()){
            case R.id.option_1_filter:
                item.setChecked(true);
                mApiService.getListWithFilterHour();
                mainFragment.refreshUI();
                break;
            case R.id.option_2_filter:
                mApiService.getListWithFilterPlace();
               mainFragment.refreshUI();
                item.setChecked(true);
                break;
        }
    }

    private void configureAndShowMainFragment(){

        mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_activity_main);

        if(mainFragment == null){
            mainFragment = new MainFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_layout_activity_main, mainFragment)
                    .commit();
        }
    }
    private void configureAndShowDetailFragment(){

        /** configure the meeting to display if is a large screen */
        if(mApiService.getMeetings().size() > 0 && mApiService.getMeetingToDisplay() == null) {
            mApiService.setMeetingToDisplay(mApiService.getMeetings().get(mApiService.getMeetings().size() - 1));
        }

        detailFragment = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_activity_detail);

        if(detailFragment == null && findViewById(R.id.frame_layout_activity_detail) != null) {
            detailFragment = new DetailFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_layout_activity_detail, detailFragment)
                    .commit();
        }

    }

    public void refreshFragmentDetail (){

        detailFragment = new DetailFragment();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout_activity_detail, detailFragment)
                .commit();
    }

}
