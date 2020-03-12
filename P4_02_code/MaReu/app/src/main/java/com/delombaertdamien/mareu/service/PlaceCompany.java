package com.delombaertdamien.mareu.service;

import java.util.Arrays;
import java.util.List;

abstract class PlaceCompany {

    private static List<String> placeOfCompany = Arrays.asList("A","B","C","D","E","F","G","H","I","J");

    static List<String> getPlaceAvailable(){

        return placeOfCompany;
    }
}
