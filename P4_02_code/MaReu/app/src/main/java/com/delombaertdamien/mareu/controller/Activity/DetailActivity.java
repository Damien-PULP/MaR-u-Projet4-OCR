package com.delombaertdamien.mareu.controller.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.delombaertdamien.mareu.R;
import com.delombaertdamien.mareu.controller.Fragment.DetailFragment;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        configureActionBar();
        configureAndShowFragment();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void configureActionBar (){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void configureAndShowFragment (){

        DetailFragment mDetailFragment = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_activity_detail);

        if(mDetailFragment == null){

            mDetailFragment = new DetailFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_layout_activity_detail, mDetailFragment)
                    .commit();

        }
    }

}
