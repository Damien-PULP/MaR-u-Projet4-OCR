package com.delombaertdamien.mareu.service;

import com.delombaertdamien.mareu.model.Metting;

import java.util.ArrayList;
import java.util.List;

public class DummyMettingApiService implements MettingApiService {

    private List<Metting> mettings = new ArrayList<>();

    @Override
    public List<Metting> getMettings() {
        return mettings;
    }

    @Override
    public void addMetting() {
    mettings.add(DummyMettingApiServiceGenerator.getMettingRandom(mettings));
    }

    @Override
    public void removeMetting(Metting metting) {
        mettings.remove(metting);
    }
}
