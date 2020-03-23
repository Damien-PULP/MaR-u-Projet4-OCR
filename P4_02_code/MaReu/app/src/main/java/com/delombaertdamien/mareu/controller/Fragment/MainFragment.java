package com.delombaertdamien.mareu.controller.Fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.delombaertdamien.mareu.DI.DI;
import com.delombaertdamien.mareu.R;
import com.delombaertdamien.mareu.controller.Activity.DetailActivity;
import com.delombaertdamien.mareu.events.StartShowDetailEvent;
import com.delombaertdamien.mareu.service.MeetingApiService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends BaseFragment {


    MeetingApiService mApiService = DI.getMettingApiService();

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    protected BaseFragment newInstance() {
        return new MainFragment();
    }

    @Override
    protected int getFragmentLayout() {
        return 0;
    }

    @Override
    protected void configureDesign() {

    }

    @Override
    protected void updateDesign() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_main, container, false);
    }



    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void showDetail(StartShowDetailEvent event){

        mApiService.setMeetingToDisplay(mApiService.getMeetingWithHashCode(event.getId()));
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        startActivity(intent);
    }


}
