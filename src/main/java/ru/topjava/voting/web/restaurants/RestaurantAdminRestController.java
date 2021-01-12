package ru.topjava.voting.web.restaurants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.topjava.voting.model.Restaurant;
import ru.topjava.voting.repository.RestRepository;

import java.net.URI;

import static ru.topjava.voting.util.ValidationUtil.checkNew;
import static ru.topjava.voting.util.ValidationUtil.checkNotFoundWithId;

@RestController
@RequestMapping(value = RestaurantAdminRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantAdminRestController {
    static final String REST_URL = "/rest/admin/rests";

    @Autowired
    protected RestRepository repository;

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        repository.delete(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> create(@RequestBody Restaurant rest) {
        checkNew(rest);
        Assert.notNull(rest, "restaurant must not be null");
        Restaurant created = repository.save(rest);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Restaurant rest, @PathVariable int id) {
        Assert.notNull(rest, "Restaurant must not be null");
        checkNotFoundWithId(repository.save(rest), rest.id());
    }



}
