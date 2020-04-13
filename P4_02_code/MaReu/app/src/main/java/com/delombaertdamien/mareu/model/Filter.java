package com.delombaertdamien.mareu.model;

import java.util.ArrayList;
import java.util.List;

public class Filter {

    private List<String> placesInFilter = new ArrayList<>();
    private List<Integer> hourInFilter =  new ArrayList<>();

    public void addHour (Integer hour){
        hourInFilter.add(hour);
    }
    public void removeHour (Integer hour){
        hourInFilter.remove(hour);
    }

    public void addPlace (String place){
        placesInFilter.add(place);
    }
    public void removePlace (String place){
        placesInFilter.remove(place);
    }

    public boolean isPlaceInFilter (String place) {
        for(int i = 0; i < placesInFilter.size(); i++){
            if(place.equals(placesInFilter.get(i))){
                return false;
            }
        }
        return true;
    }
    public boolean isHourInFilter (int startHour, int endHour) {

        for(int i = 0; i < hourInFilter.size(); i++){
            if(startHour == hourInFilter.get(i) || endHour == hourInFilter.get(i)){
                return false;
            }
        }
        return true;
    }
}
