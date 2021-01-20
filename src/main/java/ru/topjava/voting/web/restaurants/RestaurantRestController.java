package ru.topjava.voting.web.restaurants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.topjava.voting.model.Restaurant;
import ru.topjava.voting.repository.RestRepository;

import java.time.LocalDate;
import java.util.List;

import static ru.topjava.voting.util.ValidationUtil.checkNotFoundWithId;

@RestController
@RequestMapping(value = RestaurantRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantRestController {
    private static final Logger log = LoggerFactory.getLogger(RestaurantRestController.class);
    static final String REST_URL = "/rest/profile/rests";

    @Autowired
    protected RestRepository repository;

    @GetMapping("/{id}")
    public Restaurant get(@PathVariable int id) {
        log.info("get restaurant {}", id);
        return checkNotFoundWithId(repository.get(id), id);
    }

    @GetMapping
    public List<Restaurant> getAll() {
        log.info("get all restaurants");
        return repository.getAll();
    }

    @GetMapping("/with-dishes")
    public List<Restaurant> getAllWithDishesByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("get all restaurants with dishes by date {}", date);
        return repository.getAllWithDishesByDate(date);
    }

    @GetMapping("/{id}/with-dishes")
    public Restaurant getWithDishesByDate(@PathVariable int id,
                                          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("get restaurant {} with dishes by date {}", id, date);
        return checkNotFoundWithId(repository.getWithDishesByDate(id, date), id);
    }

    @GetMapping("/with-votes")
    public List<Restaurant> getAllWithVotesByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("get all restaurants with votes by date {}", date);
        return repository.getAllWithVotesByDate(date);
    }
}
