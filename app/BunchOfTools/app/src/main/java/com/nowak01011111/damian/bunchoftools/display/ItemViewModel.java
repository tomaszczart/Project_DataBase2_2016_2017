package com.nowak01011111.damian.bunchoftools.display;

/**
 * Created by utche on 21.01.2017.
 */

public class ItemViewModel {
    private int id;
    private String information1;
    private String information2;

    public ItemViewModel(String information1, String information2) {
        this.information1 = information1;
        this.information2 = information2;
        this.id = -1;
    }
    public ItemViewModel(String information1, String information2, int id) {
        this(information1,information2);
        this.id = id;
    }

    public String getInformation1() {
        return information1;
    }
    public String getInformation2() {
        return information2;
    }
    public int getId() {
        return id;
    }
}
