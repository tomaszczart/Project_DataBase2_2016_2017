package com.nowak01011111.damian.bunchoftools.entity;

import com.nowak01011111.damian.bunchoftools.R;

/**
 * Created by utche on 30.10.2016.
 */

public class Model {
    private int id;
    private String name;
    private String description;
    private float pricePerHour;
    private Category category;

    private int imageResourceId;

    //TODO: delete in near future (when api is ready)
    public final static String LOREM = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam efficitur ipsum in placerat molestie. Fusce quis mauris a enim sollicitudin ultrices non eget velit. Aliquam eu pulvinar enim.";
    public static final Model[] models = {
            new Model(1, "Axe", LOREM, 5, Category.categories[0], R.drawable.axe),
            new Model(2, "Brush", LOREM, 5, Category.categories[0], R.drawable.brush),
            new Model(3, "Drill", LOREM, 5, Category.categories[0], R.drawable.drill),
            new Model(4, "Hammer", LOREM, 10, Category.categories[1], R.drawable.hammer),
            new Model(5, "Saw", LOREM, 4, Category.categories[2], R.drawable.saw)
    };

    private Model(int id, String name, String description, float pricePerHour, Category category, int imageResourceId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.pricePerHour = pricePerHour;
        this.category = category;

        this.imageResourceId = imageResourceId;

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public float getPricePerHour() {
        return pricePerHour;
    }

    public Category getCategory() {
        return category;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

}
