package com.delombaertdamien.mareu.service;

import com.delombaertdamien.mareu.model.Metting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

abstract class DummyMettingApiServiceGenerator {

    public static final List<String> DUMMY_CONTRIBUTORS = Arrays.asList(
         "thomas.leblond@lamzone.com", "lucie.lamis@lamzone.com", "eric.lefer@lamzone.com","paul.pigo@lamzone.com","henry.tom@lamzone.com", "mathieu.gerald@lamzone.com",
            "stephanie.joly@lamzone.com","emma.lilo@lamzone.com","josef.ledoux@lamzone.com"
    );
    public static final List<String> DUMMY_PLACE = Arrays.asList(
       "A","B","C","D","E","F","G","H","I","J"
    );
    public static final List<String> DUMMY_SUBJECT = Arrays.asList(
        "Budget","Nouveauté","Communication","Informatique","Stock","Journalière","RH"
    );

    static Metting getMettingRandom ( List<Metting> mettingList){

        float hour = getHourRandom();
        String place = getPlaceRandom();
        String subject = getSubjectRandom();
        List<String> contributor = getContributorRandom();

        return new Metting(mettingList.size(), subject, contributor, hour, place);
    }

    static float getHourRandom (){

        Random rdm = new Random();
        float hourRandom = 7f + (rdm.nextFloat() * ( 19f- 7f));
        String hourString = String.format("%.2f", hourRandom);
        float mHourFloat = Float.parseFloat(hourString);
        return mHourFloat;
    }
    static String getPlaceRandom (){

        List<String> listPlace = DUMMY_PLACE;

        Random rdm = new Random();
        String place = listPlace.get(rdm.nextInt(listPlace.size()));
        return place;
    }
    static String getSubjectRandom (){

        List<String> listPlace = DUMMY_SUBJECT;

        Random rdm = new Random();
        String place = listPlace.get(rdm.nextInt(listPlace.size()));
        return place;
    }
    static List<String> getContributorRandom (){

        List<String> listPlace = DUMMY_CONTRIBUTORS;

        Random rdm = new Random();
        int nbContributor = 1 + rdm.nextInt(5);

        List<String> contributors = new ArrayList<>();

        for(int i = 0; i < nbContributor; i++) {
            contributors.add(listPlace.get(rdm.nextInt(listPlace.size())));
        }
        return contributors;
    }
}
