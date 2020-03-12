package com.delombaertdamien.mareu.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.delombaertdamien.mareu.DI.DI;
import com.delombaertdamien.mareu.service.MeetingApiService;
import com.delombaertdamien.mareu.view.AdaptorNonScrollListView;
import com.delombaertdamien.mareu.view.NonScrollListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ConfigureMeetingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText mTextSubject;
    private EditText mTextContributor;
    private NonScrollListView mListContributor;
    private Button mButtonValidContributor;
    private Spinner mSpinnerPlace;
    private TimePicker mTimePicker;
    private Button mButtonValidMeeting;

    private List<String> contributors = new ArrayList<>();
    private String placeSelected;

    private MeetingApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_meeting);
        mApiService = DI.getMettingApiService();
        configureUI();

    }

    private void configureUI (){

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTextSubject = findViewById(R.id.configure_activity_edit_text_subject);
        mTextContributor = findViewById(R.id.configure_activity_edit_text_contributor);
        mButtonValidContributor = findViewById(R.id.configure_activity_button_valid_contributor);
        mSpinnerPlace = findViewById(R.id.configure_activity_spinner_place);
        /** Duty Fixed*/
        mTimePicker = findViewById(R.id.configure_activity_time_picker_hour);
        mTimePicker.setIs24HourView(true);

        mButtonValidMeeting = findViewById(R.id.configure_activity_edit_text_valid_meeting);

        mListContributor = findViewById(R.id.configure_activity_list_contributor);
        refreshUIListContributor();

        // Creating adapter for spinner
        /** Duty Fixed*/
        refreshUISpinnerPlace();

        /** Duty Fixed*/
        mTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {

               refreshUISpinnerPlace();
            }
        });

        mButtonValidContributor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = mTextContributor.getText().toString();
                if(!value.equals("") && value.endsWith("@lamzlo.com")){
                    contributors.add(value);
                    refreshUIListContributor();
                    mTextContributor.setText("@lamzlo.com");
                }else{
                    showAlertWithMsg("L'adresse email doit se terminer par @lamzlo.com");
                    //Snackbar.make(view, R.string.msg_snackbar_email_adv, Snackbar.LENGTH_LONG ).setAction("Action", null).show();
                }
            }
        });
        /** Duty Fixed*/
        mButtonValidMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validMeeting();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_configure_meeting, menu);
        // Associate searchable configuration with the SearchView
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

    private void validMeeting (){
        String subject = mTextSubject.getText().toString();
        if(!subject.equals("") && contributors.size() > 0 && !placeSelected.equals("")){
            mApiService.addMeeting(getRandomColor(),subject, placeSelected, contributors, mTimePicker.getHour(), mTimePicker.getMinute());
            Log.d("ConfigureActivity","Size Of mMettings : " + mApiService.getMeetings().size());
            Intent mIntent = new Intent(ConfigureMeetingActivity.this, MainActivity.class);
            startActivity(mIntent);
        }else{
            showAlertWithMsg("Veuillez remplir tout les champs !");
            //Snackbar.make(this.findViewById(android.R.id.content) , "Veuillez remplir tout les champs !", Snackbar.LENGTH_LONG ).setAction("Action", null).show();
        }
    }

    private void refreshUIListContributor (){
        AdaptorNonScrollListView adaptor = new AdaptorNonScrollListView(this, contributors, this);
        mListContributor.setAdapter(adaptor);
    }
    private void refreshUISpinnerPlace (){

        float hour = (float)(mTimePicker.getHour() + ( mTimePicker.getMinute() /60f));
        Log.d("ConfigureMeeting", "The hour is : " + hour);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mApiService.getListPlaceAvailable(hour));
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mSpinnerPlace.setAdapter(adapter);
        mSpinnerPlace.setOnItemSelectedListener(this);
    }
    public void removeAnContributor ( String contributor){
        contributors.remove(contributor);
        refreshUIListContributor();
    }
    public int getRandomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
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
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        //set title
        alertDialogBuilder.setTitle("Erreur");

        //set dialog message
        alertDialogBuilder
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                });

        //create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        //show it
        alertDialog.show();
    }
}
