package com.nowak01011111.damian.bunchoftools.entity;

import com.nowak01011111.damian.bunchoftools.R;

/**
 * Created by utche on 30.10.2016.
 */

public class Tools {
    private String name;
    private int imageResourceId;

    //TODO: delete in near future (when api is ready)
    public static final Tools[] tools = {
            new Tools("Axe", R.drawable.axe),
            new Tools("Brush", R.drawable.brush),
            new Tools("Drill", R.drawable.drill),
            new Tools("Hammer", R.drawable.hammer),
            new Tools("Saw", R.drawable.saw)
    };

    private Tools(String name, int imageResourceId){
        this.name = name;
        this.imageResourceId = imageResourceId;
    }

    public String getName(){
        return name;
    }
    public int getImageResourceId(){
        return imageResourceId;
    }
}
