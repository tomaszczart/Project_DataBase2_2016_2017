package com.nowak01011111.damian.bunchoftools.entity;

import java.util.Date;

/**
 * Created by utche on 06.01.2017.
 */

public class Transaction {

    private int id;
    private Customer customer;
    private Employee employee;
    private Date beginDate;
    private Date endDate;

    public Transaction(int id, Customer customer, Employee employee, Date beginDate, Date endDate) {
        this.id = id;
        this.customer = customer;
        this.employee = employee;
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

}
