package com.nowak01011111.damian.bunchoftools.entity;
import com.nowak01011111.damian.bunchoftools.R;

/**
 * Created by utche on 30.10.2016.
 */

public class Model {
    private int id;
    private String name;
    private int imageResourceId;

    //TODO: delete in near future (when api is ready)
    public static final Model[] models = {
            new Model(1,"Axe", R.drawable.axe),
            new Model(2,"Brush", R.drawable.brush),
            new Model(3,"Drill", R.drawable.drill),
            new Model(4,"Hammer", R.drawable.hammer),
            new Model(5,"Saw", R.drawable.saw)
    };

    private Model(int id, String name, int imageResourceId){
        this.id = id;
        this.name = name;
        this.imageResourceId = imageResourceId;
    }

    public String getName(){
        return name;
    }
    public int getImageResourceId(){
        return imageResourceId;
    }
    public int getId(){
        return id;
    }
}
