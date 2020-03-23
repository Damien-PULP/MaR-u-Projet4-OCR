package com.delombaertdamien.mareu.controller.Activity;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.delombaertdamien.mareu.DI.DI;
import com.delombaertdamien.mareu.controller.Fragment.TimePickerFragment;
import com.delombaertdamien.mareu.service.MeetingApiService;
import com.delombaertdamien.mareu.view.AdaptorNonScrollListView;
import com.delombaertdamien.mareu.view.NonScrollListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.delombaertdamien.mareu.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConfigureMeetingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, TimePickerDialog.OnTimeSetListener {

    //UI Components
    @BindView(R.id.configure_activity_edit_text_subject)
    EditText mTextSubject;
    @BindView(R.id.configure_activity_edit_text_contributor)
    EditText mTextContributor;
    @BindView(R.id.configure_activity_list_contributor)
    NonScrollListView mListContributor;
    @BindView(R.id.configure_activity_button_valid_contributor)
    Button mButtonValidContributor;
    @BindView(R.id.configure_activity_spinner_place)
    Spinner mSpinnerPlace;
    @BindView(R.id.configure_activity_button_set_start_clock)
    Button mButtonStartSetClock;
    @BindView(R.id.configure_activity_button_set_end_clock)
    Button mButtonEndSetClock;
    @BindView(R.id.configure_activity_edit_text_valid_meeting)
    Button mButtonValidMeeting;

    private List<String> contributors = new ArrayList<>();

    private String placeSelected;
    private Calendar mStartHour;
    private Calendar mEndHour;

    private boolean isStartHour = true;

    private MeetingApiService mApiService;

    SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_meeting);

        ButterKnife.bind(this);

        mApiService = DI.getMettingApiService();

        mStartHour = (Calendar) format.getCalendar().clone();
        mEndHour = (Calendar) format.getCalendar().clone();

        configureUI();

    }

    private void configureUI (){

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Adaptor NonScrollListView email contributor
        AdaptorNonScrollListView adaptor = new AdaptorNonScrollListView(this, contributors, this);
        mListContributor.setAdapter(adaptor);

        refreshUISpinnerPlace();

        /** Duty Fixed*/
        mButtonStartSetClock.setOnClickListener(view -> {
            isStartHour = true;
            DialogFragment fragmentClock = new TimePickerFragment();
            fragmentClock.show(getSupportFragmentManager(), "start time picker");
        });
        mButtonEndSetClock.setOnClickListener(view -> {
            isStartHour = false;
            DialogFragment fragmentClock = new TimePickerFragment();
            fragmentClock.show(getSupportFragmentManager(), "end time picker");
        });

        mButtonValidContributor.setOnClickListener(view -> {
            String value = mTextContributor.getText().toString();
            if(!value.equals("") && value.endsWith("@lamzlo.com")){
                contributors.add(value);
                ((AdaptorNonScrollListView)mListContributor.getAdapter()).notifyDataSetChanged();

                mTextContributor.setText("@lamzlo.com");
            }else{
                showAlertWithMsg("L'adresse email doit se terminer par @lamzlo.com");
            }
        });

        mButtonValidMeeting.setOnClickListener(view -> validMeeting());
    }
    private void refreshUISpinnerPlace (){

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_place, mApiService.getListPlaceAvailable(mStartHour, mEndHour));

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_item_place);
        // Apply the adapter to the spinner
        mSpinnerPlace.setAdapter(adapter);
        mSpinnerPlace.setOnItemSelectedListener(this);
    }

    public void removeAnContributor (String contributor){
        contributors.remove(contributor);
        ((AdaptorNonScrollListView)mListContributor.getAdapter()).notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_configure_meeting, menu);
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        String itemSelected = parent.getItemAtPosition(position).toString();
        placeSelected = itemSelected;
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //DO NOTHING
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_add_meeting_menu:
                validMeeting();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void showAlertWithMsg (String msg){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle("Erreur");

        alertDialogBuilder
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();
    }
    public int getRandomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    private void validMeeting (){

        String subject = mTextSubject.getText().toString();
        if(!subject.equals("") && contributors.size() > 0 && !placeSelected.equals("")){

            mApiService.addMeeting(getRandomColor(),subject, placeSelected, contributors, mStartHour,  mEndHour);

            Intent mIntent = new Intent(ConfigureMeetingActivity.this, MainActivity.class);
            startActivity(mIntent);
        }else{
            showAlertWithMsg("Veuillez remplir tout les champs !");
        }
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int min) {

        if(isStartHour) {

            mStartHour.set(Calendar.HOUR_OF_DAY, hour);
            mStartHour.set(Calendar.MINUTE, min);

            if(mButtonEndSetClock.getText().equals("définir l'heure de fin")){

                mEndHour.setTime(mStartHour.getTime());
                mEndHour.add(Calendar.MINUTE, 45);

            }

        }else{

            mEndHour.set(Calendar.HOUR_OF_DAY, hour);
            mEndHour.set(Calendar.MINUTE, min);
        }
        refreshHour();
        refreshUISpinnerPlace();

    }

    private void refreshHour (){
        mButtonStartSetClock.setText("La réunion commence à " + format.format(mStartHour.getTime()));
        mButtonEndSetClock.setText("La réunion ce termine à " + format.format(mEndHour.getTime()));
    }

}
