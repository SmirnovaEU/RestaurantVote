package ru.topjava.voting.repository;

import ru.topjava.voting.model.Restaurant;

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

    Restaurant getWithDishesByDate(int id, LocalDate date);

    List<Restaurant> getAllWithDishesByDate(LocalDate date);

}
