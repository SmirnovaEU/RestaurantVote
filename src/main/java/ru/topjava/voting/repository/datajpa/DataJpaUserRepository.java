package ru.topjava.voting.repository.datajpa;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import ru.topjava.voting.model.User;
import ru.topjava.voting.repository.UserRepository;
import ru.topjava.voting.web.AuthorizedUser;

import java.util.List;

import static ru.topjava.voting.util.UserUtil.prepareToSave;

@Repository("userService")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class DataJpaUserRepository implements UserRepository, UserDetailsService {
    private static final Sort SORT_NAME_EMAIL = Sort.by(Sort.Direction.ASC, "name", "email");

    private final CrudUserRepository crudRepository;
    private final PasswordEncoder passwordEncoder;

    public DataJpaUserRepository(CrudUserRepository crudRepository, PasswordEncoder passwordEncoder) {
        this.crudRepository = crudRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @CacheEvict(value = "users", allEntries = true)
    public User save(User user) {
        return crudRepository.save(prepareToSave(user, passwordEncoder));
    }

    @Override
    @CacheEvict(value = "users", allEntries = true)
    public boolean delete(int id) {
        return crudRepository.delete(id) != 0;
    }

    @Override
    public User get(int id) {
        return crudRepository.findById(id).orElseThrow();
    }

    @Override
    public User getByEmail(String email) {
        return crudRepository.getByEmail(email);
    }

    @Override
    @Cacheable("users")
    public List<User> getAll() {
        return crudRepository.findAll(SORT_NAME_EMAIL);
    }

    @Override
    public User getWithVotes(int id) {
        return crudRepository.getWithVotes(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = getByEmail(email.toLowerCase());
        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " is not found");
        }
        return new AuthorizedUser(user);
    }
}
