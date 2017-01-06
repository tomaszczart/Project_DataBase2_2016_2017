package com.nowak01011111.damian.bunchoftools.entity;

/**
 * Created by utche on 06.01.2017.
 */

public class Category {
    private int id;
    private String name;
    private String description;

    //TODO: delete in near future (when api is ready)
    public final static String LOREM = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam efficitur ipsum in placerat molestie. Fusce quis mauris a enim sollicitudin ultrices non eget velit. Aliquam eu pulvinar enim.";
    public static final Category[] categories = {
            new Category(1, "Cat1", LOREM),
            new Category(2, "Cat2", LOREM),
            new Category(3, "Cat3", LOREM),
            new Category(4, "Cat4", LOREM),
            new Category(5, "Cat5", LOREM)
    };

    public Category(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
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
}
