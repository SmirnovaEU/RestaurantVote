package ru.topjava.restaurant.repository;

import ru.topjava.restaurant.model.Vote;

import java.time.LocalDate;
import java.util.List;

public interface VoteRepository {
    // null if updated vote do not belong to userId
    Vote save(Vote vote, int userId);

    // false if vote do not belong to userId
    boolean delete(int id, int userId);

    // null if vote do not belong to userId
    Vote get(int id, int userId);

    // null if not found
    Vote getByDate(LocalDate date, int userId);

    // ORDERED date desc
    List<Vote> getAll(int userId);

    default Vote getWithUser(int id, int userId) {
        throw new UnsupportedOperationException();
    }
}
