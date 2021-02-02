package ru.topjava.voting.repository;

import ru.topjava.voting.model.Dish;

import java.time.LocalDate;
import java.util.List;

public interface DishRepository {
    // null if update dish do not belong to restId
    Dish save(Dish dish, int restId);

    // false if dish do not belong to restId
    boolean delete(int id, int restId);

    // null if dish do not belong to restId
    Dish get(int id, int restId);

    // ORDERED BY name
    List<Dish> getAllByDate(int restId, LocalDate date);

}
