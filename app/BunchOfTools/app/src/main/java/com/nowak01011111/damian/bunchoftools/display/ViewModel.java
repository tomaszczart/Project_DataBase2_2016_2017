package com.nowak01011111.damian.bunchoftools.display;

/**
 * Created by utche on 30.10.2016.
 */

public class ViewModel {
    private int id;
    private String name;
    private String description;
    private String information1;
    private int image;

    public ViewModel(String name,String description, String information1) {
        this.name = name;
        this.description = description;
        this.information1 = information1;
        this.id = -1;
    }
    public ViewModel(String name,String description, String information1, int id) {
        this(name,description,information1);
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public String getInformation1() {
        return information1;
    }
    public int getId() {
        return id;
    }
    public int getImage() {
        return image;
    }


}
