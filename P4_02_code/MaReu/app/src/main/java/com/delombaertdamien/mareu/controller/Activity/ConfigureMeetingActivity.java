package com.delombaertdamien.mareu.controller.Activity;

import android.app.TimePickerDialog;
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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.delombaertdamien.mareu.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConfigureMeetingActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, TimePickerDialog.OnTimeSetListener {

    //UI Components
    @BindView(R.id.configure_activity_edit_text_subject)
    TextInputLayout mTextSubject;
    @BindView(R.id.configure_activity_edit_text_contributor)
    TextInputLayout mTextContributor;
    @BindView(R.id.configure_activity_list_contributor)
    NonScrollListView mListContributor;
    @BindView(R.id.configure_activity_spinner_place)
    AutoCompleteTextView mSpinnerPlace;
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

    private final SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_meeting);

        ButterKnife.bind(this);

        mApiService = DI.getMeetingApiService();

        mStartHour = (Calendar) format.getCalendar().clone();
        mEndHour = (Calendar) format.getCalendar().clone();

        configureUI();

    }

    public void onItemClick (AdapterView<?> parent, View view, int position, long id){
        placeSelected = parent.getItemAtPosition(position).toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_configure_meeting, menu);
        return true;
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

    private void configureUI (){

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AdaptorNonScrollListView adaptor = new AdaptorNonScrollListView(this, contributors, this);
        mListContributor.setAdapter(adaptor);

        refreshUISpinnerPlace();

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

        mTextContributor.setEndIconOnClickListener(view -> {

            String value = mTextContributor.getEditText().getText().toString();
            if(!value.equals("") && value.endsWith("@lamzone.com")){
                contributors.add(value);
                ((AdaptorNonScrollListView)mListContributor.getAdapter()).notifyDataSetChanged();

                mTextContributor.getEditText().setText("@lamzone.com");
            }else{
                showAlertWithMsg("L'adresse email doit se terminer par @lamzone.com");
            }
        });

        mButtonValidMeeting.setOnClickListener(view -> validMeeting());
    }
    private void refreshUISpinnerPlace (){

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item_place, mApiService.getListPlaceAvailable(mStartHour, mEndHour));

        adapter.setDropDownViewResource(R.layout.spinner_item_place);
        mSpinnerPlace.setAdapter(adapter);

        /** if the place selected after time and time changed after place selected */
        boolean isAvailable = false;
        for(int i = 0; i < adapter.getCount(); i++){
            if(mSpinnerPlace.getText().equals(adapter.getItem(i))){
                isAvailable = true;
            }
        }
        if(!isAvailable){
            mSpinnerPlace.setText("");
        }

        mSpinnerPlace.setOnItemClickListener(this);
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

    public void removeAnContributor (String contributor){

        contributors.remove(contributor);
        ((AdaptorNonScrollListView)mListContributor.getAdapter()).notifyDataSetChanged();
    }

    private void showAlertWithMsg (String msg){

        MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(this);

        alertDialogBuilder.setTitle("Erreur");

        alertDialogBuilder
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("Ok", (dialog, id) -> dialog.cancel());

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();
    }
    private int getRandomColor() {

        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    private void validMeeting (){

        String subject = mTextSubject.getEditText().getText().toString();

        if(!subject.equals("") && contributors.size() > 0 && !placeSelected.equals("")){

            mApiService.addMeeting(getRandomColor(),subject, placeSelected, contributors, mStartHour,  mEndHour);

            Intent mIntent = new Intent(ConfigureMeetingActivity.this, MainActivity.class);
            startActivity(mIntent);
        }else{
            showAlertWithMsg("Veuillez remplir tout les champs !");
        }
    }

}
