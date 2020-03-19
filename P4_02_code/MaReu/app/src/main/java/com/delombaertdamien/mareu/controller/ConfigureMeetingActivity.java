package com.delombaertdamien.mareu.controller;

import android.app.TimePickerDialog;
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

import java.text.DateFormat;
import java.text.ParseException;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_meeting);

        ButterKnife.bind(this);

        mApiService = DI.getMettingApiService();

        SimpleDateFormat format = new SimpleDateFormat("hh:mm");

        mStartHour = format.getCalendar();
        mEndHour = format.getCalendar();
        configureUI();

    }

    private void configureUI (){

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Adaptor NonScrollListView email contributor
        AdaptorNonScrollListView adaptor = new AdaptorNonScrollListView(this, contributors, this);
        mListContributor.setAdapter(adaptor);

        refreshUISpinnerPlace(16f);

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
    private void refreshUISpinnerPlace (float hour){

        float f1 = mStartHour.get(Calendar.HOUR) + mStartHour.get(Calendar.MINUTE);
        float f2 = mEndHour.get(Calendar.HOUR) + mEndHour.get(Calendar.MINUTE);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mApiService.getListPlaceAvailable(f1, f2));
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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

            mApiService.addMeeting(getRandomColor(),subject, placeSelected, contributors, mStartHour,  mStartHour);

            Intent mIntent = new Intent(ConfigureMeetingActivity.this, MainActivity.class);
            startActivity(mIntent);
        }else{
            showAlertWithMsg("Veuillez remplir tout les champs !");
        }
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int min) {

        /**SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");

        Calendar date = Calendar.getInstance();
        date.clear();
        date.set(Calendar.HOUR, hour);
        date.set(Calendar.MINUTE, min);*/

       //Date dateShow = null;
        //dateShow = displayFormat.parse()
        if(isStartHour) {
            //TODO FIX ME
            //mStartHour = date;

            mStartHour.set(Calendar.HOUR, hour);
            mStartHour.set(Calendar.MINUTE, min);
            float time = hour + (min / 60);
            refreshUISpinnerPlace(time);

            if(mButtonEndSetClock.getText().equals("définir l'heure de fin")){

                Log.d("Configure", "Heure avant : " + hour + " H " + min);
                Log.d("Configure", "Calendar Start avant : " + mStartHour.get(Calendar.HOUR) + " H " + mStartHour.get(Calendar.MINUTE));
                int hourEnd = hour;
                int minEnd = min;

                if(min >= 15){
                    hourEnd  += 1;
                    minEnd -= - 15;
                }else{
                     hourEnd = hour;
                     minEnd += 45;
                }
                //TODO FIX ME

                // TODO : change hour of start !!!
                mEndHour.set(Calendar.HOUR, hourEnd);
                mEndHour.set(Calendar.MINUTE, minEnd);

                Log.d("Configure", "Heure apres : " + hour + " H " + min);
                Log.d("Configure", "Calendar Start apres : " + mStartHour.get(Calendar.HOUR) + " H " + mStartHour.get(Calendar.MINUTE));
                mButtonEndSetClock.setText("La réunion ce termine à " + mEndHour.get(Calendar.HOUR) + "H" + mEndHour.get(Calendar.MINUTE));
            }
            mButtonStartSetClock.setText("La réunion commence à " + mStartHour.get(Calendar.HOUR) + "H" + mStartHour.get(Calendar.MINUTE) );
        }else{
            //TODO FIX ME
            mEndHour.set(Calendar.HOUR, hour);
            mEndHour.set(Calendar.MINUTE, min);
            mButtonEndSetClock.setText("La réunion ce termine à " + mEndHour.get(Calendar.HOUR) + "H" + mEndHour.get(Calendar.MINUTE));
        }

    }


}
