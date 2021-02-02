package ru.topjava.voting.to;

import ru.topjava.voting.model.Restaurant;
import ru.topjava.voting.model.User;

import java.time.LocalDate;
import java.time.LocalTime;

public class VoteTo {
    private LocalDate date;
    private LocalTime time;
    private User user;
    private Restaurant restaurant;

    public VoteTo(LocalDate date, LocalTime time, User user, Restaurant restaurant) {
        this.date = date;
        this.time = time;
        this.user = user;
        this.restaurant= restaurant;
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
        return restaurant;
    }

    public void setRest(Restaurant restaurant) {
        this.restaurant= restaurant;
    }

    @Override
    public String toString() {
        return "VoteTo{" +
                "date=" + date +
                ", time=" + time +
                ", user=" + user +
                ", restaurant=" + restaurant+
                '}';
    }
}
