package com.yifactory.daocheapp.widget.addressSelected.entity;

import java.util.List;

public class CarNumberCity implements LinkageSecond<Void> {
    private String name;

    public CarNumberCity(String name) {
        this.name = name;
    }

    @Override
    public Object getId() {
        return name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<Void> getThirds() {
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof CarNumberCity)) {
            return false;
        }
        CarNumberCity obj1 = (CarNumberCity) obj;
        return name.equals(obj1.getName());
    }

    @Override
    public String toString() {
        return "name=" + name;
    }

}