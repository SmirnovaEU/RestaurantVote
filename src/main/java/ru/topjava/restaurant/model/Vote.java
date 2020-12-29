package ru.topjava.restaurant.model;

import java.time.LocalDate;

public class Vote extends AbstractBaseEntity {
    private User user;
    private Restaurant rest;
    private LocalDate date;

    public Vote() {

    }

    public Vote(Integer id, Restaurant rest, LocalDate date) {
        super(id);
        this.rest = rest;
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Restaurant getRest() {
        return rest;
    }

    public void setRest(Restaurant rest) {
        this.rest = rest;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", user=" + user +
                ", rest=" + rest +
                ", date=" + date +
                '}';
    }
}
