package com.example.booking.model;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


@Getter
public class Theater {

    private String id;
    private String name;

    List<Screen> screens = new ArrayList<>();

    public Theater(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addScreen(Screen screen){
        this.screens.add(screen);
    }

    // other metadata
}
