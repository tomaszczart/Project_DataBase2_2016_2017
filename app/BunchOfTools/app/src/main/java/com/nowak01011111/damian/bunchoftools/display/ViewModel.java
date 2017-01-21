package com.nowak01011111.damian.bunchoftools.display;

import android.graphics.Bitmap;

/**
 * Created by utche on 30.10.2016.
 */

public class ViewModel {
    private int id;
    private String name;
    private String description;
    private String information1;
    private String bitmapPath;

    public ViewModel(String name,String description, String information1, String bitmapPath) {
        this.name = name;
        this.description = description;
        this.information1 = information1;
        this.bitmapPath = bitmapPath;
        this.id = -1;
    }
    public ViewModel(String name,String description, String information1,String bitmapPath, int id) {
        this(name,description,information1,bitmapPath);
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
    public String getBitmapPath() {
        return bitmapPath;
    }


}
