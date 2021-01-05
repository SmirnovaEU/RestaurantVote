package ru.topjava.restaurant.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import ru.topjava.restaurant.model.User;
import ru.topjava.restaurant.repository.UserRepository;

import java.util.List;

import static ru.topjava.restaurant.util.ValidationUtil.assureIdConsistent;
import static ru.topjava.restaurant.util.ValidationUtil.checkNew;

public class AbstractUserController {

    @Autowired
    private UserRepository repository;

    public List<User> getAll() {
     //   log.info("getAll");
        return repository.getAll();
    }

    public User get(int id) {
   //     log.info("get {}", id);
        return repository.get(id);
    }

    public User create(User user) {
  //      log.info("create {}", user);
        checkNew(user);
        return repository.save(user);
    }

    public void delete(int id) {
  //      log.info("delete {}", id);
        repository.delete(id);
    }

    public void update(User user, int id) {
  //      log.info("update {} with id={}", user, id);
        assureIdConsistent(user, id);
        repository.save(user);
    }

    public User getByMail(String email) {
  //      log.info("getByEmail {}", email);
        return repository.getByEmail(email);
    }

    
}
