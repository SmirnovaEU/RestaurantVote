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
        return repository.getAll();
    }

    public User get(int id) {
        return repository.get(id);
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
