package ru.topjava.restaurant.repository;

import ru.topjava.restaurant.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

public interface RestRepository {
    // null if not found, when updated
    Restaurant save(Restaurant restaurant);

    // false if not found
    boolean delete(int id);

    // null if not found
    Restaurant get(int id);

    List<Restaurant> getAll();

    default Restaurant getWithDishesByDate(int id, LocalDate date) {
        throw new UnsupportedOperationException();
    }

    default List<Restaurant> getAllWithDishesByDate(LocalDate date) {
        throw new UnsupportedOperationException();
    }

    default List<Restaurant> getAllWithVotesByDate(LocalDate date) {
        throw new UnsupportedOperationException();
    }
}
