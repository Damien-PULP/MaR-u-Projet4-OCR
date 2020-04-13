package com.delombaertdamien.mareu.events;

public class StartShowDetailEvent {

    private Integer id;
    public StartShowDetailEvent(Integer id_string) {
        this.id = id_string;
    }

    public Integer getId (){
        return id;
    }
}
