package ru.topjava.restaurant.to;

import ru.topjava.restaurant.model.Restaurant;
import ru.topjava.restaurant.model.User;

import java.time.LocalDate;
import java.time.LocalTime;

public class VoteTo {
    private LocalDate date;
    private LocalTime time;
    private User user;
    private Restaurant rest;

    public VoteTo(LocalDate date, LocalTime time, User user, Restaurant rest) {
        this.date = date;
        this.time = time;
        this.user = user;
        this.rest = rest;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
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

    @Override
    public String toString() {
        return "VoteTo{" +
                "date=" + date +
                ", time=" + time +
                ", user=" + user +
                ", rest=" + rest +
                '}';
    }
}
