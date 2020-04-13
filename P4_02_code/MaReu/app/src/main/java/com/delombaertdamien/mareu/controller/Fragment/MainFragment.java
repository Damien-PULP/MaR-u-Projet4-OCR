package com.delombaertdamien.mareu.controller.Fragment;


import android.content.Intent;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.delombaertdamien.mareu.DI.DI;
import com.delombaertdamien.mareu.R;
import com.delombaertdamien.mareu.controller.Activity.ConfigureMeetingActivity;
import com.delombaertdamien.mareu.controller.Activity.DetailActivity;
import com.delombaertdamien.mareu.controller.Activity.MainActivity;
import com.delombaertdamien.mareu.events.StartShowDetailEvent;
import com.delombaertdamien.mareu.model.Meeting;
import com.delombaertdamien.mareu.service.MeetingApiService;
import com.delombaertdamien.mareu.view.AdaptorListView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends BaseFragment {

    //UI Components
    @BindView(R.id.floating_action_button_add_meeting)
    FloatingActionButton mFabAddMeeting;
    @BindView(R.id.list_meeting_main_activity)
    RecyclerView mRecyclerView;

    private AdaptorListView mAdaptor;

    private final MeetingApiService mApiService = DI.getMeetingApiService();

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    protected BaseFragment newInstance() {
        return new MainFragment();
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_main;
    }

    @Override
    protected void configureDesign() {

        List<Meeting> mMeetings = mApiService.getMeetingsToShow();

        mAdaptor = new AdaptorListView(mMeetings);
        mRecyclerView.setAdapter(mAdaptor);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mFabAddMeeting.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), ConfigureMeetingActivity.class);
            startActivity(intent);
        });

    }

    @Override
    protected void updateDesign() {
        refreshUI();
    }

    public void refreshUI() {
        mAdaptor.notifyDataSetChanged();
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
    public void showDetail(StartShowDetailEvent event) {

        mApiService.setMeetingToDisplay(mApiService.getMeetingWithHashCode(event.getId()));
        if (getActivity().findViewById(R.id.frame_layout_activity_detail) == null) {
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            startActivity(intent);
        }else{
            ((MainActivity)getActivity()).refreshFragmentDetail();
        }
    }

}
