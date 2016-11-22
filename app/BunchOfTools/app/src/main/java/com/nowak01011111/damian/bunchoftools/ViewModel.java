package com.nowak01011111.damian.bunchoftools;

/**
 * Created by utche on 30.10.2016.
 */

public class ViewModel {
    private String text;
    private int image;

    public ViewModel(String text, int image) {
        this.text = text;
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public int getImage() {
        return image;
    }
}
