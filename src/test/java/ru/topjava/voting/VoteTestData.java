package ru.topjava.voting;

import ru.topjava.voting.model.Restaurant;
import ru.topjava.voting.model.Vote;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static java.time.LocalDate.of;
import static ru.topjava.voting.model.AbstractBaseEntity.START_SEQ;

public class VoteTestData {
    public static final TestMatcher<Vote> VOTE_MATCHER = TestMatcher.usingIgnoringFieldsComparator(Vote.class, "user", "rest");

    public static final int VOTE1_ID = START_SEQ + 5;
    public static final int REST1_ID = START_SEQ + 2;

    public static final Restaurant rest1 = new Restaurant(REST1_ID, "Buusa");
    public static final Restaurant rest2 = new Restaurant(REST1_ID + 1, "White Sushi");
    public static final Restaurant rest3 = new Restaurant(REST1_ID + 2, "Papa Jones");

    public static final Vote vote1 = new Vote(VOTE1_ID, rest1, of(2020, Month.JANUARY, 30));
    public static final Vote vote2 = new Vote(VOTE1_ID + 1, rest2, of(2020, Month.JANUARY, 31));
    public static final Vote vote3 = new Vote(VOTE1_ID + 2, rest3, of(2020, Month.JANUARY, 30));
    public static final Vote vote4 = new Vote(VOTE1_ID + 3, rest1, of(2020, Month.JANUARY, 31));
    public static final Vote vote5 = new Vote(VOTE1_ID + 4, rest2, of(2021, Month.JANUARY, 9));

    public static final List<Vote> userVotes = List.of(vote5, vote2, vote1);

    public static Vote getNew() {
        return new Vote(null, null, LocalDate.now());
    }

    public static Vote getUpdated() {
        return new Vote(VOTE1_ID, rest2, vote1.getDate());
    }

}
