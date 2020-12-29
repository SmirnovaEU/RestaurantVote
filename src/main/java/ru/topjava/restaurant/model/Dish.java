package ru.topjava.restaurant.model;

import java.time.LocalDate;

public class Dish extends AbstractNamedEntity {
    private LocalDate date;
    private Restaurant rest;
    private Double price;

    public Dish() {
    }

    public Dish(Integer id, String name, LocalDate date, Restaurant rest) {
        super(id, name);
        this.date = date;
        this.rest = rest;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Restaurant getRest() {
        return rest;
    }

    public void setRest(Restaurant rest) {
        this.rest = rest;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", rest=" + rest +
                ", price=" + price +
                '}';
    }
}
