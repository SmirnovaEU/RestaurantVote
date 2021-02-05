package ru.topjava.voting.web.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.topjava.voting.model.User;
import ru.topjava.voting.repository.datajpa.CrudUserRepository;
import ru.topjava.voting.to.UserTo;
import ru.topjava.voting.util.UserUtil;
import ru.topjava.voting.util.exception.NotFoundException;
import ru.topjava.voting.web.AbstractControllerTest;
import ru.topjava.voting.web.json.JsonUtil;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.topjava.voting.TestUtil.userHttpBasic;
import static ru.topjava.voting.UserTestData.*;
import static ru.topjava.voting.web.user.ProfileRestController.REST_URL;

class ProfileRestControllerTest extends AbstractControllerTest {
    private static final Sort SORT_NAME_EMAIL = Sort.by(Sort.Direction.ASC, "name", "email");

    @Autowired
    private CrudUserRepository userRepository;

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(user)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_MATCHER.contentJson(user));
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL)
                .with(userHttpBasic(user)))
                .andExpect(status().isNoContent());
        USER_MATCHER.assertMatch(userRepository.findAll(SORT_NAME_EMAIL), admin);
    }

    @Test
    void update() throws Exception {
        UserTo updatedTo = new UserTo(null, "newName", "newemail@ya.ru", "newPassword");
        perform(MockMvcRequestBuilders.put(REST_URL).contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(user))
                .content(JsonUtil.writeValue(updatedTo)))
                .andDo(print())
                .andExpect(status().isNoContent());

        USER_MATCHER.assertMatch(userRepository.findById(USER_ID)
                        .orElseThrow(() -> new NotFoundException("There is no such user"))
                , UserUtil.updateFromTo(new User(user), updatedTo));
    }
}