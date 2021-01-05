package ru.topjava.restaurant.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "votes", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "date"}, name = "votes_unique_user_date_idx")})
public class Vote extends AbstractBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rest_id", nullable = false)
    @NotNull
    private Restaurant rest;

    @Column(name = "date", nullable = false)
    @NotNull
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
