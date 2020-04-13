package com.delombaertdamien.mareu.controller.Fragment;


import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.delombaertdamien.mareu.DI.DI;
import com.delombaertdamien.mareu.R;
import com.delombaertdamien.mareu.model.Meeting;
import com.delombaertdamien.mareu.service.MeetingApiService;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    //UI Components
    @BindView(R.id.fragment_detail_subject)
    TextView mTextSubject;
    @BindView(R.id.fragment_detail_contributor)
    TextView mTextContributor;
    @BindView(R.id.fragment_detail_place)
    TextView mTextPlace;
    @BindView(R.id.fragment_detail_hour)
    TextView mTextHour;

    private final MeetingApiService mApiService = DI.getMeetingApiService();
    private Meeting mMeetingToDisplay;

    private final SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.getDefault());

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View result=inflater.inflate(R.layout.fragment_detail, container, false);

        ButterKnife.bind(this, result);
        mMeetingToDisplay = mApiService.getMeetingToDisplay();
        configureUI();

        return result;
    }

    private void configureUI(){

        if(mMeetingToDisplay != null){

            mTextSubject.setText(mMeetingToDisplay.getSubject());

            String listContributor = "";
            List<String> listContributorOfMeeting = mMeetingToDisplay.getContributors();

            for(int i = 0; i < listContributorOfMeeting.size(); i++){
                listContributor += listContributorOfMeeting.get(i);
                if(i < listContributorOfMeeting.size() - 1){
                    listContributor += "\n";
                }
            }
            mTextContributor.setText(listContributor);

            mTextPlace.setText("Local " +mMeetingToDisplay.getPlace());
            mTextHour.setText(format.format(mMeetingToDisplay.getStartHourOfMeeting().getTime()) + " - " +format.format(mMeetingToDisplay.getEndHourOfMeeting().getTime()));
            ((AppCompatActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(mMeetingToDisplay.getId()));

        }else{

            mTextSubject.setText("");
            mTextContributor.setText("");
            mTextPlace.setText("");
            mTextHour.setText("");
        }
    }


}
