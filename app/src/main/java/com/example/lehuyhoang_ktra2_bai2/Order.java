package com.example.lehuyhoang_ktra2_bai2;

import java.io.Serializable;

public class Order implements Serializable {
    private int id, pack;
    private String name, start_location, start_date;

    public Order(int pack, String name, String start_location, String start_date) {
        this.pack = pack;
        this.name = name;
        this.start_location = start_location;
        this.start_date = start_date;
    }

    public Order(int id, int pack, String name, String start_location, String start_date) {
        this.id = id;
        this.pack = pack;
        this.name = name;
        this.start_location = start_location;
        this.start_date = start_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPack() {
        return pack;
    }

    public void setPack(int pack) {
        this.pack = pack;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStart_location() {
        return start_location;
    }

    public void setStart_location(String start_location) {
        this.start_location = start_location;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }
}
