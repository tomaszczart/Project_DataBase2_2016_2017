package com.nowak01011111.damian.bunchoftools.display;

/**
 * Created by utche on 06.01.2017.
 */

public class SimpleViewModel {
    private int id;
    private String name;
    private String description;

    public SimpleViewModel(String name, String description) {
        this.name = name;
        this.description = description;
        this.id = -1;
    }
    public SimpleViewModel(String name, String description, int id) {
        this(name,description);
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public int getId() {
        return id;
    }

}
