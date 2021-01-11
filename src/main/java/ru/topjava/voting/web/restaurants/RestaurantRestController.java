package ru.topjava.voting.web.restaurants;

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
    static final String REST_URL = "/rest/profile/rests";

    @Autowired
    protected RestRepository repository;

    @GetMapping("/{id}")
    public Restaurant get(@PathVariable int id) {
        return checkNotFoundWithId(repository.get(id), id);
    }

    @GetMapping
    public List<Restaurant> getAll() {
        return repository.getAll();
    }

    @GetMapping("/with-dishes")
    public List<Restaurant> getAllWithDishesByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return repository.getAllWithDishesByDate(date);
    }

    @GetMapping("/{id}/with-dishes")
    public Restaurant getWithDishesByDate(@PathVariable int id,
                                          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return checkNotFoundWithId(repository.getWithDishesByDate(id, date), id);
    }

    @GetMapping("/with-votes")
    public List<Restaurant> getAllWithVotesByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return repository.getAllWithVotesByDate(date);
    }
}
