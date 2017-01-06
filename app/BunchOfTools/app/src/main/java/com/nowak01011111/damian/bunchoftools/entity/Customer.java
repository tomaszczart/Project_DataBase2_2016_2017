package com.nowak01011111.damian.bunchoftools.entity;

/**
 * Created by utche on 06.01.2017.
 */

public class Customer {
    private int id;
    private int reservationCounter;
    private String name;
    private String address;
    private String email;
    private String phone;

    public Customer(int id, int reservationCounter, String name, String address, String email, String phone) {
        this.id = id;
        this.reservationCounter = reservationCounter;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public int getReservationCounter() {
        return reservationCounter;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}
