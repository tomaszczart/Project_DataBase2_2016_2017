package com.nowak01011111.damian.bunchoftools;

/**
 * Created by utche on 30.10.2016.
 * TODO: this class is temporary, to delete in near future (when api is ready)
 */

public class Tools {
    private String name;
    private int imageResourceId;

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
