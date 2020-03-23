package com.delombaertdamien.mareu.events;

public class ShowDetailEvent {

    private Integer id;
    public ShowDetailEvent(Integer id_string) {
        this.id = id_string;
    }

    public Integer getId (){
        return id;
    }
}
