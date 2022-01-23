package com.gmail.kutepov89.sergey;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name="Menu")
@NamedQueries({
        @NamedQuery(name="Menu.findAll", query = "SELECT c FROM MenuRestaurant c"),
        @NamedQuery(name="Menu.findBetween", query = "SELECT c FROM MenuRestaurant c WHERE c.cost BETWEEN :val1 AND :val2"),
        @NamedQuery(name="Menu.withDiscount", query = "SELECT c FROM MenuRestaurant c WHERE c.discount != 0")
})
public class MenuRestaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private long id;

    @Column(name = "dishName", nullable = false)
    private String dishName;

    @Column(name = "cost", nullable = false)
    private double cost;

    @Column(name = "weight", nullable = false)
    private double weight;

    private double discount;

    public MenuRestaurant() {}

    public MenuRestaurant(String dishName, double cost, double weight, double discount) {
        this.dishName = dishName;
        this.cost = cost;
        this.weight = weight;
        this.discount = discount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "MenuRestaurant{" +
                "id=" + id +
                ", dishName='" + dishName + '\'' +
                ", cost=" + cost +
                ", weight=" + weight +
                ", discount=" + discount +
                '}';
    }
}
