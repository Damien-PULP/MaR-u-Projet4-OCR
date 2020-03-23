package com.delombaertdamien.mareu.controller.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.delombaertdamien.mareu.R;
import com.delombaertdamien.mareu.controller.Fragment.DetailFragment;
import com.delombaertdamien.mareu.controller.Fragment.MainFragment;
import com.delombaertdamien.mareu.events.ShowDetailEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

public class DetailActivity extends AppCompatActivity {

    private DetailFragment detailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        configureAndShowFragment();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
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

    private void configureAndShowFragment (){

        detailFragment = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_activity_detail);

        if(detailFragment == null){

            detailFragment = new DetailFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_layout_activity_detail, detailFragment)
                    .commit();

        }
    }

}
