package ru.manturov.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.manturov.entity.User;
import ru.manturov.security.UserRole;

import java.util.List;

import static org.junit.Assert.*;

@DataJpaTest
@RunWith(SpringRunner.class)
public class UserRepositoryTest {

    @Autowired
    UserRepository subj;

    @Test
    public void findByEmail_EmailNotFound() {
        User user = subj.findByEmail("vazy@gmail.com").orElse(null);
        assertNull(user);
    }

    @Test
    public void findByEmail_EmailFound() {
        User user = subj.findByEmail("vazyan93@gmail.com").orElse(null);

        assertNotNull(user);
        assertEquals(Long.valueOf(1L), user.getId());
        assertEquals("vazyan93@gmail.com", user.getEmail());
        assertEquals("$2a$10$/MItPFGhSuS83WqAtMQ13OYP5/v.pWocZlppM6g66GsTlVWVC197u", user.getPassword());
    }

    @Test
    public void findByFilter() {
        UserFilter filter = new UserFilter().setEmailLike("vazyan93%")
                .setUserRole(UserRole.USER);

        List<User> users = subj.findByFilter(filter);

        assertNotNull(users);
        assertEquals(1, users.size());
    }
}
