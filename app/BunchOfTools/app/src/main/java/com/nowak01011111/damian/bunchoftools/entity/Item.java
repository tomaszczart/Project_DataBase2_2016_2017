package com.nowak01011111.damian.bunchoftools.entity;

/**
 * Created by utche on 06.01.2017.
 */

public class Item {

    private int modelId;
    private int id;
    private Condition condition;
    private Status status;

    public Item(int id, int modelId, Condition condition, Status status) {
        this.id = id;
        this.modelId = modelId;
        this.condition = condition;
        this.status = status;
    }

    public int getModelId() {
        return modelId;
    }

    public int getId() {
        return id;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public enum Condition {
        Bad,
        Normal,
        Good,
    }

    public enum Status {
        Available,
        Rent,
        Reserved,
    }


}