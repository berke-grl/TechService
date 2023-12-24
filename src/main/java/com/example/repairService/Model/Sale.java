package com.example.repairService.Model;

public class Sale {
    private long id;
    private String note;
    private float price;
    private long product_id;

    public Sale() {
    }

    public Sale(String note, float price, long product_id) {
        this.note = note;
        this.price = price;
        this.product_id = product_id;
    }

    public Sale(long id, String note, float price, long product_id) {
        this.id = id;
        this.note = note;
        this.price = price;
        this.product_id = product_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(long product_id) {
        this.product_id = product_id;
    }
}
