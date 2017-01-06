package com.nowak01011111.damian.bunchoftools.entity;

import java.util.Date;

/**
 * Created by utche on 06.01.2017.
 */

public class Reservation {

    private int id;
    private Customer customer;
    private Item item;
    private Date date;

    public Reservation(int id, Customer customer, Item item, Date date) {
        this.id = id;
        this.customer = customer;
        this.item = item;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Item getItem() {
        return item;
    }

    public Date getDate() {
        return date;
    }

}
