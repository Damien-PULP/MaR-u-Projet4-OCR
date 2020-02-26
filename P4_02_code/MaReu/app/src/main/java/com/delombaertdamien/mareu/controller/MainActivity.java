package com.delombaertdamien.mareu.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.delombaertdamien.mareu.DI.DI;
import com.delombaertdamien.mareu.R;
import com.delombaertdamien.mareu.model.Metting;
import com.delombaertdamien.mareu.service.MettingApiService;
import com.delombaertdamien.mareu.view.AdaptorListView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    //UI Components
    @BindView(R.id.floating_action_button_add_meeting)
    FloatingActionButton mFabAddMetting;
    @BindView(R.id.list_metting_main_activity)
    RecyclerView mRecyclerView;

    MettingApiService mApiService;
    private List<Metting> mMettings;
    private AdaptorListView mAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        /** Replace ButterKnife*/
        mRecyclerView = findViewById(R.id.list_metting_main_activity);
        mFabAddMetting = findViewById(R.id.floating_action_button_add_meeting);

        mApiService = DI.getMettingApiService();
        configureUI();
    }

    private void configureUI(){

        /**mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));*/


        Init();

        mFabAddMetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            mApiService.addMetting();
            Init();
            }
        });
    }

    private void Init(){
        mMettings = mApiService.getMettings();

        mAdaptor = new AdaptorListView(mMettings, this);
        mRecyclerView.setAdapter(mAdaptor);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        return true;
    }
}
