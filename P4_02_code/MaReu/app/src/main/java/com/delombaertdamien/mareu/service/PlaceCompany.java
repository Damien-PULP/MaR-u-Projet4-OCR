package com.delombaertdamien.mareu.service;

import java.util.Arrays;
import java.util.List;

public abstract class PlaceCompany {

    private static final List<String> placeOfCompany = Arrays.asList("A","B","C","D","E","F","G","H","I","J");

    public static List<String> getPlaceAvailable(){

        return placeOfCompany;
    }
}
