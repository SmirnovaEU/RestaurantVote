package ru.topjava.restaurant.web.vote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.topjava.restaurant.model.Restaurant;
import ru.topjava.restaurant.model.Vote;
import ru.topjava.restaurant.repository.RestRepository;
import ru.topjava.restaurant.repository.VoteRepository;
import ru.topjava.restaurant.web.SecurityUtil;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.topjava.restaurant.util.ValidationUtil.*;

@RestController
@RequestMapping(value = VoteRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteRestController {
    static final String REST_URL = "/rest/profile/votes";
    static final LocalTime STOP_VOTING_TIME = LocalTime.of(11, 0, 0);

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private RestRepository restRepository;

    @GetMapping("/by")
    public Vote getByDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        int userId = SecurityUtil.authUserId();
        //  log.info("get vote for user {} for date {}", userId, date);
        return voteRepository.getByDate(date, userId);
    }
//User can't delete his votes
//    @DeleteMapping("/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void delete(@PathVariable int id) {
//        int userId = SecurityUtil.authUserId();
//    //    log.info("delete meal {} for user {}", id, userId);
//        repository.delete(id, userId);
//    }

    @GetMapping
    public List<Vote> getAll() {
        int userId = SecurityUtil.authUserId();
        //   log.info("getAll for user {}", userId);
        return voteRepository.getAll(userId);
    }

    @GetMapping("/today")
    public Vote getTodayVote() {
        LocalDate currentDate = LocalDate.now();
        int userId = SecurityUtil.authUserId();
        return voteRepository.getByDate(currentDate, userId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> create(@RequestBody Vote vote) {
        int userId = SecurityUtil.authUserId();
        checkNew(vote);
        //    log.info("create {} for user {}", meal, userId);

        Vote created = voteRepository.save(vote, userId);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

//    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseStatus(value = HttpStatus.NO_CONTENT)
//    public void update(@RequestBody Vote vote, @PathVariable int id) {
//        int userId = SecurityUtil.authUserId();
//        assureIdConsistent(vote, id);
//        //    log.info("update {} for user {}", meal, userId);
//        checkNotFoundWithId(repository.save(vote, userId), vote.id());
//    }

    @PutMapping(value = "/change", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, @RequestParam("restId") int restId) {
        LocalTime localTime = LocalTime.now();
        if (localTime.compareTo(STOP_VOTING_TIME) > 0) {
            return;
        }
        int userId = SecurityUtil.authUserId();
        Vote vote = getByDate(date);
        checkNotFound(vote, "date = " + date);
        Restaurant rest = restRepository.get(restId);
        checkNotFoundWithId(rest, restId);
        vote.setRest(rest);
        //    log.info("update {} for user {}", meal, userId);
        voteRepository.save(vote, userId);
    }
}
