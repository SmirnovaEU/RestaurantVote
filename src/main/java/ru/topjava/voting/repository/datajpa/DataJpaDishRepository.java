package ru.topjava.voting.repository.datajpa;

import org.springframework.stereotype.Repository;
import ru.topjava.voting.model.Dish;
import ru.topjava.voting.repository.DishRepository;
import ru.topjava.voting.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

@Repository
public class DataJpaDishRepository implements DishRepository {

    private final CrudDishRepository crudDishRepository;
    private final CrudRestRepository crudRestRepository;

    public DataJpaDishRepository(CrudDishRepository crudDishRepository, CrudRestRepository crudRestRepository) {
        this.crudDishRepository = crudDishRepository;
        this.crudRestRepository = crudRestRepository;
    }

    @Override
    public Dish save(Dish dish, int restId) {
        if (!dish.isNew() && get(dish.getId(), restId) == null) {
            return null;
        }
        dish.setRestaurant(crudRestRepository.getOne(restId));
        return crudDishRepository.save(dish);
    }

    @Override
    public boolean delete(int id, int restId) {
        return crudDishRepository.delete(id, restId) != 0;
    }

    @Override
    public Dish get(int id, int restId) {
        return crudDishRepository.findById(id)
                .filter(dish -> dish.getRestaurant().getId() == restId)
                .orElseThrow(() -> new NotFoundException("There is no such dish"));
    }

    @Override
    public List<Dish> getAllByDate(int restId, LocalDate date) {
        return crudDishRepository.getAllByDate(restId, date);
    }

}
