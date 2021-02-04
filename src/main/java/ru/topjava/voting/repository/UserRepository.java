package ru.topjava.voting.repository;

import ru.topjava.voting.model.User;

import java.time.LocalDate;
import java.util.List;

public interface UserRepository {
    // null if not found, when updated
    User save(User user);

    // false if not found
    boolean delete(int id);

    // null if not found
    User get(int id);

    // null if not found
    User getByEmail(String email);

    List<User> getAll();

    User getWithVotesByDate(int id, LocalDate date);
}
