package ru.topjava.voting.web.dish;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.topjava.voting.model.Dish;
import ru.topjava.voting.repository.datajpa.CrudDishRepository;
import ru.topjava.voting.repository.datajpa.CrudRestaurantRepository;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;

import static ru.topjava.voting.util.ValidationUtil.*;

@RestController
@RequestMapping(value = DishAdminRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class DishAdminRestController {
    private static final Logger log = LoggerFactory.getLogger(DishAdminRestController.class);
    static final String REST_URL = "/rest/admin/rests/{restId}/dishes";

    @Autowired
    private CrudDishRepository dishRepository;

    @Autowired
    private CrudRestaurantRepository restaurantRepository;

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id, @PathVariable("restId") int restId) {
        log.info("delete dish {} for restaurant {}", id, restId);
        checkNotFoundWithId(dishRepository.delete(id, restId), id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Dish dish, @PathVariable("id") int id,
                       @PathVariable("restId") int restId) {
        assureIdConsistent(dish, id);
        Assert.notNull(dish, "dish must not be null");
        LocalDate date = dishRepository.get(id, restId).getDate();
        dish.setDate(date);
        log.info("update dish {} for restaurant {}", id, restId);
        checkNotFoundWithId(save(dish, restId), dish.id());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> create(@Valid @RequestBody Dish dish,
                                       @PathVariable("restId") int restId) {
        checkNew(dish);
        Assert.notNull(dish, "dish must not be null");
        log.info("create dish {} for restaurant {}", dish, restId);
        Dish created = save(dish, restId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(restId, created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    public Dish save(Dish dish, int restId) {
        if (!dish.isNew() && dishRepository.get(dish.getId(), restId) == null) {
            return null;
        }
        dish.setRestaurant(restaurantRepository.getOne(restId));
        return dishRepository.save(dish);
    }

}
