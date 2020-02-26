package com.delombaertdamien.mareu.service;

import com.delombaertdamien.mareu.model.Metting;

import java.util.List;

public interface MettingApiService {

    List<Metting> getMettings();
    void addMetting();
    void removeMetting(Metting metting);
}
