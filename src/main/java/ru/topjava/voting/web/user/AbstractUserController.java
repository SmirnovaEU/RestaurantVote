package ru.topjava.voting.web.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.topjava.voting.model.User;
import ru.topjava.voting.repository.UserRepository;
import ru.topjava.voting.to.UserTo;
import ru.topjava.voting.util.UserUtil;

import java.util.List;

import static ru.topjava.voting.util.ValidationUtil.*;

public class AbstractUserController {
    private static final Logger log = LoggerFactory.getLogger(AbstractUserController.class);

    @Autowired
    private UserRepository repository;

    public List<User> getAll() {
        log.info("getAll");
        return repository.getAll();
    }

    public User get(int id) {
        log.info("get {}", id);
        return repository.get(id);
    }

    public User getWithVotes(int id) {
        log.info("get {} with votes", id);
        return checkNotFoundWithId(repository.getWithVotes(id), id);
    }

    @Transactional
    public User create(User user) {
        log.info("create {}", user);
        checkNew(user);
        return repository.save(user);
    }

    @Transactional
    public void delete(int id) {
        log.info("delete {}", id);
        repository.delete(id);
    }

    @Transactional
    public void update(User user, int id) {
        log.info("update {} with id={}", user, id);
        assureIdConsistent(user, id);
        repository.save(user);
    }

    @Transactional
    public void update(UserTo userTo) {
        log.info("update {}", userTo);
        User user = get(userTo.id());
        UserUtil.updateFromTo(user, userTo);
    }

    public User getByMail(String email) {
        log.info("getByEmail {}", email);
        return repository.getByEmail(email);
    }

}
