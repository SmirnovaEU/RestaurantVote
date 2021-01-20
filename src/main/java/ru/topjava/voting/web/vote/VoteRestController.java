package ru.topjava.voting.web.vote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.topjava.voting.model.Restaurant;
import ru.topjava.voting.model.Vote;
import ru.topjava.voting.repository.RestRepository;
import ru.topjava.voting.repository.VoteRepository;
import ru.topjava.voting.web.SecurityUtil;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.topjava.voting.util.ValidationUtil.*;

@RestController
@RequestMapping(value = VoteRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteRestController {
    static final String REST_URL = "/rest/profile/votes";
    static final LocalTime STOP_VOTING_TIME = LocalTime.of(11, 0, 0);
    private static final Logger log = LoggerFactory.getLogger(VoteRestController.class);

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private RestRepository restRepository;

    @GetMapping("/by")
    public Vote getByDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        int userId = SecurityUtil.authUserId();
        log.info("get vote by date {} for user {}", date, userId);
        return voteRepository.getByDate(date, userId);
    }

    @GetMapping
    public List<Vote> getAll() {
        int userId = SecurityUtil.authUserId();
        log.info("get all votes for user {}", userId);
        return voteRepository.getAll(userId);
    }

    @GetMapping("/today")
    public Vote getTodayVote() {
        LocalDate currentDate = LocalDate.now();
        int userId = SecurityUtil.authUserId();
        log.info("get vote for today for user {}", userId);
        return voteRepository.getByDate(currentDate, userId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> create(@RequestBody Vote vote) {
        int userId = SecurityUtil.authUserId();
        checkNew(vote);
        log.info("create vote {} for user {}", vote, userId);
        Vote created = voteRepository.save(vote, userId);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/change")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                       @RequestParam("restId") int restId) {
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
        log.info("update vote for date {} for user {}", date, userId);
        voteRepository.save(vote, userId);
    }
}
