package ru.topjava.voting.web.dish;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.topjava.voting.model.Dish;
import ru.topjava.voting.repository.DishRepository;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = DishRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class DishRestController {
    private static final Logger log = LoggerFactory.getLogger(DishRestController.class);
    static final String REST_URL = "/rest/profile/rests/{restId}/dishes";

    @Autowired
    private DishRepository dishRepository;

    @GetMapping("/{id}")
    public Dish get(@PathVariable("id") int id, @PathVariable("restId") int restId) {
        log.info("get dish {} for restaurant {}", id, restId);
        return dishRepository.get(id, restId);
    }

    @GetMapping
    public List<Dish> getAllByDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                   @PathVariable("restId") int restId) {
        log.info("get all dishes by date {} for restaurant {}", date, restId);
        return dishRepository.getAllByDate(restId, date);
    }


}
