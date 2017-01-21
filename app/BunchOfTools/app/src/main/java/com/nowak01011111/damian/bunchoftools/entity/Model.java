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
    private int categoryId;
    private String imageUrl;

    public Model(int id, String name, String description, float pricePerHour, int categoryId, String imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.pricePerHour = pricePerHour;
        this.categoryId = categoryId;
        this.imageUrl = imageUrl;
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

    public int getCategoryId() {
        return categoryId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

}
