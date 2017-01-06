package com.nowak01011111.damian.bunchoftools.entity;

/**
 * Created by utche on 06.01.2017.
 */

public class Item {

    private Model model;
    private int id;
    private Condition condition;
    private Status status;

    //TODO: delete in near future (when api is ready)
    public static final Item[] items = {
            new Item(1, Model.models[0], Condition.GOOD, Status.AVAILABLE),
            new Item(2, Model.models[0], Condition.GOOD, Status.AVAILABLE),
            new Item(3, Model.models[0], Condition.GOOD, Status.AVAILABLE),
            new Item(4, Model.models[0], Condition.GOOD, Status.AVAILABLE),
            new Item(5, Model.models[0], Condition.GOOD, Status.AVAILABLE),
    };


    public Item(int id, Model model, Condition condition, Status status ){
        this.id = id;
        this.model = model;
        this.condition = condition;
        this.status = status;
    }

    public Model getModel(){
        return  model;
    }
    public int getId(){
        return id;
    }
    public Condition getCondition(){
        return condition;
    }
    public void setCondition(Condition condition){
        this.condition = condition;
    }
    public Status getStatus() {return status;}
    public void setStatus(Status status){this.status = status;}
}

enum Condition {
    BAD,
    NORMAL,
    GOOD,
}

enum Status {
    AVAILABLE,
    RENTED,
    RESERVED,
}

