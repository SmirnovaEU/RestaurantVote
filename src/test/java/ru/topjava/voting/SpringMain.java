package ru.topjava.voting;

import org.springframework.context.support.GenericXmlApplicationContext;
import ru.topjava.voting.model.Role;
import ru.topjava.voting.model.User;
import ru.topjava.voting.web.user.AdminRestController;

import java.util.Arrays;

import static ru.topjava.voting.TestUtil.mockAuthorize;
import static ru.topjava.voting.UserTestData.user;

public class SpringMain {
    public static void main(String[] args) {
        try (GenericXmlApplicationContext appCtx = new GenericXmlApplicationContext()) {
            appCtx.load("spring/inmemory.xml");
            appCtx.refresh();

            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ADMIN));
            System.out.println();

            mockAuthorize(user);

        }
    }
}
