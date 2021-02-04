package ru.topjava.voting.web.restaurants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.topjava.voting.model.Restaurant;
import ru.topjava.voting.repository.RestRepository;

import javax.validation.Valid;
import java.net.URI;

import static ru.topjava.voting.util.ValidationUtil.*;

@RestController
@RequestMapping(value = RestaurantAdminRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantAdminRestController {
    private static final Logger log = LoggerFactory.getLogger(RestaurantAdminRestController.class);
    static final String REST_URL = "/rest/admin/restaurants";

    @Autowired
    protected RestRepository repository;

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete restaurant {}", id);
        repository.delete(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> create(@Valid @RequestBody Restaurant rest) {
        checkNew(rest);
        Assert.notNull(rest, "restaurant must not be null");
        Restaurant created = repository.save(rest);
        log.info("create restaurant {}", rest);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Restaurant rest, @PathVariable int id) {
        assureIdConsistent(rest, id);
        Assert.notNull(rest, "Restaurant must not be null");
        log.info("update restaurant {}", id);
        checkNotFoundWithId(repository.save(rest), rest.id());
    }



}
