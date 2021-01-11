package ru.topjava.restaurant.web.dish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.topjava.restaurant.model.Dish;
import ru.topjava.restaurant.repository.DishRepository;

import java.net.URI;
import java.time.LocalDate;

import static ru.topjava.restaurant.util.ValidationUtil.*;

@RestController
@RequestMapping(value = DishAdminRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class DishAdminRestController {
    static final String REST_URL = "/rest/admin/rests/{restId}/dishes";

    @Autowired
    private DishRepository dishRepository;

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id, @PathVariable("restId") int restId) {
        checkNotFoundWithId(dishRepository.delete(id, restId), id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Dish dish, @PathVariable("id") int id,
                       @PathVariable("restId") int restId) {
        assureIdConsistent(dish, id);
        Assert.notNull(dish, "dish must not be null");
        LocalDate date = dishRepository.get(id, restId).getDate();
        dish.setDate(date);
        checkNotFoundWithId(dishRepository.save(dish, restId), dish.id());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> create(@RequestBody Dish dish,
                                       @PathVariable("restId") int restId) {
        checkNew(dish);
        Assert.notNull(dish, "dish must not be null");
        Dish created = dishRepository.save(dish, restId);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(restId, created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

}
