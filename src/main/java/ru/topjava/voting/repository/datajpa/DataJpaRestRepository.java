package ru.topjava.voting.repository.datajpa;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.topjava.voting.model.Restaurant;
import ru.topjava.voting.repository.RestRepository;

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
    @CacheEvict(value = "restaurants", allEntries = true)
    public Restaurant save(Restaurant restaurant) {
        return crudRepository.save(restaurant);
    }

    @Override
    @CacheEvict(value = "restaurants", allEntries = true)
    public boolean delete(int id) {
        return crudRepository.delete(id) != 0;
    }

    @Override
    public Restaurant get(int id) {
        return crudRepository.findById(id).orElseThrow();
    }

    @Override
    @Cacheable("restaurants")
    public List<Restaurant> getAll() {
        return crudRepository.findAll(SORT_NAME);
    }

    @Override
    @Cacheable("restaurants")
    public List<Restaurant> getAllWithDishesByDate(LocalDate date) {
        return crudRepository.getAllWithDishesByDate(date);
    }

    @Override
    @Cacheable("restaurants")
    public Restaurant getWithDishesByDate(int id, LocalDate date) {
        return crudRepository.getWithDishesByDate(id, date);
    }
}
