package ru.topjava.voting.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.topjava.voting.model.User;
import ru.topjava.voting.repository.UserRepository;
import ru.topjava.voting.to.UserTo;
import ru.topjava.voting.util.UserUtil;

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

    @Transactional
    public User create(User user) {
        checkNew(user);
        return repository.save(user);
    }

    @Transactional
    public void delete(int id) {
        repository.delete(id);
    }

    @Transactional
    public void update(User user, int id) {
        assureIdConsistent(user, id);
        repository.save(user);
    }

    @Transactional
    public void update(UserTo userTo) {
        User user = get(userTo.id());
        UserUtil.updateFromTo(user, userTo);
    }

    public User getByMail(String email) {
        return repository.getByEmail(email);
    }

}
