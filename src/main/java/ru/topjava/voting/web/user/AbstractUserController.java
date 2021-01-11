package ru.topjava.voting.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import ru.topjava.voting.model.User;
import ru.topjava.voting.repository.UserRepository;

import java.util.List;

import static ru.topjava.voting.util.ValidationUtil.*;

public class AbstractUserController {

    @Autowired
    private UserRepository repository;

    public List<User> getAll() {
        return repository.getAll();
    }

    public User get(int id) {
        return repository.get(id);
    }

    public User getWithVotes(int id) {
        return checkNotFoundWithId(repository.getWithVotes(id), id);
    }

    public User create(User user) {
        checkNew(user);
        return repository.save(user);
    }

    public void delete(int id) {
        repository.delete(id);
    }

    public void update(User user, int id) {
        assureIdConsistent(user, id);
        repository.save(user);
    }

    public User getByMail(String email) {
        return repository.getByEmail(email);
    }


}
