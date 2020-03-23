package com.delombaertdamien.mareu.controller.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.delombaertdamien.mareu.DI.DI;
import com.delombaertdamien.mareu.R;
import com.delombaertdamien.mareu.controller.Fragment.DetailFragment;
import com.delombaertdamien.mareu.controller.Fragment.MainFragment;
import com.delombaertdamien.mareu.model.Meeting;
import com.delombaertdamien.mareu.service.MeetingApiService;
import com.delombaertdamien.mareu.view.AdaptorListView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    //UI Components
    @BindView(R.id.floating_action_button_add_meeting)
    FloatingActionButton mFabAddMeeting;
    @BindView(R.id.list_meeting_main_activity)
    RecyclerView mRecyclerView;

    private MeetingApiService mApiService;
    private List<Meeting> mMeetings;

    private AdaptorListView mAdaptor;

    private MainFragment mainFragment;
    private DetailFragment detailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureAndShowMainFragment();
        configureAndShowDetailFragment();

        ButterKnife.bind(this);
        mApiService = DI.getMettingApiService();

        Init();
        refreshUI();
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
                refreshUI();
                break;
            case R.id.option_2_filter:
                mApiService.getListWithFilterPlace();
                refreshUI();
                item.setChecked(true);
                break;
        }
    }
    private void refreshUI(){

        mAdaptor.notifyDataSetChanged();
    }
    private void Init(){

        mMeetings = mApiService.getMeetings();

        mAdaptor = new AdaptorListView(mMeetings, this);
        mRecyclerView.setAdapter(mAdaptor);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mFabAddMeeting.setOnClickListener(view -> {
            Intent intent = new Intent(this, ConfigureMeetingActivity.class);
            startActivity(intent);
        });
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

        detailFragment = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_activity_detail);

        if(detailFragment == null && findViewById(R.id.frame_layout_activity_detail) != null){

            detailFragment = new DetailFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_layout_activity_detail, detailFragment)
                    .commit();

        }
    }

}
