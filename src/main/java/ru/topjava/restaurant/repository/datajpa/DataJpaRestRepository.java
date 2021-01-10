package ru.topjava.restaurant.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.topjava.restaurant.model.Restaurant;
import ru.topjava.restaurant.repository.RestRepository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class DataJpaRestRepository implements RestRepository {
    private static final Sort SORT_NAME = Sort.by(Sort.Direction.ASC, "name");

    private final CrudRestRepository crudRepository;

    public DataJpaRestRepository(CrudRestRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        return crudRepository.save(restaurant);
    }

    @Override
    public boolean delete(int id) {
        return crudRepository.delete(id) != 0;
    }

    @Override
    public Restaurant get(int id) {
        return crudRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Restaurant> getAll() {
        return crudRepository.findAll(SORT_NAME);
    }

    @Override
    public List<Restaurant> getAllWithDishesByDate(LocalDate date) {
        return crudRepository.getAllWithDishesByDate(date);
    }

    @Override
    public List<Restaurant> getAllWithVotesByDate(LocalDate date) {
        return crudRepository.getAllWithVotesByDate(date);
    }

    @Override
    public Restaurant getWithDishesByDate(int id, LocalDate date) {
        return crudRepository.getWithDishesByDate(id, date);
    }
}
