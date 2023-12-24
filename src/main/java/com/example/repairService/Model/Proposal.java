package com.example.repairService.Model;

public class Proposal {
    private int id;
    private String note;
    private float price;
    private long user_id;
    private boolean status;
    private long product_id;

    public Proposal() {
    }

    public Proposal(String note, float price, long user_id, boolean status, long product_id) {
        this.note = note;
        this.price = price;
        this.user_id = user_id;
        this.status = status;
        this.product_id = product_id;
    }

    public Proposal(int id, String note, float price, long user_id, boolean status, long product_id) {
        this.id = id;
        this.note = note;
        this.price = price;
        this.user_id = user_id;
        this.status = status;
        this.product_id = product_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(long product_id) {
        this.product_id = product_id;
    }
}
