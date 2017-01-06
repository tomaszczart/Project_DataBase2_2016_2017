package com.nowak01011111.damian.bunchoftools.entity;

/**
 * Created by utche on 06.01.2017.
 */

public class Item {

    private Model model;
    private int id;
    private Reservation reservation;
    private Condition condition;

    //TODO: delete in near future (when api is ready)
    public static final Item[] items = {
            new Item(1, Model.models[0], Condition.GOOD),
            new Item(2, Model.models[0], Condition.GOOD),
            new Item(3, Model.models[0], Condition.GOOD),
            new Item(4, Model.models[0], Condition.GOOD),
            new Item(5, Model.models[0], Condition.GOOD),

    };


    public Item(int id, Model model, Condition condition ){
        this.id = id;
        this.model = model;
        this.condition = condition;
    }

    public Model getModel(){
        return  model;
    }

    public int getId(){
        return id;
    }

    public Reservation getReservation(){
        return reservation;
    }

    public Condition getCondition(){
        return condition;
    }

    public void setCondition(Condition condition){
        this.condition = condition;
    }

    public void setReservation(Reservation reservation){
        this.reservation = reservation;
    }


}

enum Condition {
    BAD,
    GOOD,
}

