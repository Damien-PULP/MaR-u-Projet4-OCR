package com.delombaertdamien.mareu.service;

import android.util.Log;

import com.delombaertdamien.mareu.model.Metting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DummyMettingApiService implements MettingApiService {

    private List<Metting> mettings = new ArrayList<>();

    @Override
    public List<Metting> getMettings() {
        return mettings;
    }

    @Override
    public void addMetting() {
    mettings.add(DummyMettingApiServiceGenerator.getMettingRandomAI(mettings));
    }

    @Override
    public void removeMetting(Metting metting) {
        mettings.remove(metting);
    }

    @Override
    public void getListWithFilterPlace() {
        Collections.sort(mettings, new Comparator<Metting>() {
            @Override
            public int compare(Metting t0, Metting t1) {

                return t0.getPlace().compareToIgnoreCase(t1.getPlace());
            }
        });
    }

    @Override
    public void getListWithFilterHour() {
        Collections.sort(mettings, new Comparator<Metting>() {
            @Override
            public int compare(Metting t0, Metting t1) {

                return Float.compare(t0.getHourOfMetting(), t1.getHourOfMetting());
                        //(String.valueOf(t0.getHourOfMetting())).compareToIgnoreCase(String.valueOf(t1.getHourOfMetting()));
            }
        });
        Log.d("ApiService", "The list is sorted");

    }
}
