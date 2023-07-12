package ru.manturov.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.manturov.entity.Category;
import ru.manturov.entity.User;
import ru.manturov.security.UserRole;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

@DataJpaTest
@RunWith(SpringRunner.class)
public class CategoryRepositoryTest {

    @Autowired
    CategoryRepository subj;

    @Test
    public void findAllByUserId_UserNotFound() {
        assertTrue(subj.findAllByUserId(2L).isEmpty());
    }

    @Test
    public void findAllByUserId_UserFound() {
        ArrayList<Category> categories = new ArrayList<>();

        User user = new User()
                .setId(1L)
                .setEmail("vazyan93@gmail.com")
                .setPassword("$2a$10$/MItPFGhSuS83WqAtMQ13OYP5/v.pWocZlppM6g66GsTlVWVC197u")
                .setRoles(Collections.singleton(UserRole.USER));

        Category categoryOne = new Category()
                .setId(1L)
                .setCategory("TransCategoryName")
                .setUser(user);

        Category categorytwo = new Category()
                .setId(2L)
                .setCategory("Product")
                .setUser(user);

        categories.add(categoryOne);
        categories.add(categorytwo);

        List<Category> allByUserId = subj.findAllByUserId(1L);
        assertNotNull(allByUserId);
        assertEquals(categories.size(), allByUserId.size());
        assertEquals(categories.get(0).getId(), allByUserId.get(0).getId());
        assertEquals(categories.get(0).getCategory(), allByUserId.get(0).getCategory());
        assertEquals(categories.get(0).getUser().getId(), allByUserId.get(0).getUser().getId());
        assertEquals(categories.get(1).getId(), allByUserId.get(1).getId());
        assertEquals(categories.get(1).getCategory(), allByUserId.get(1).getCategory());
        assertEquals(categories.get(1).getUser().getId(), allByUserId.get(1).getUser().getId());
    }

    @Test
    public void findById_CategoryNotFound() {
        Category category = subj.findById(3L).orElse(null);
        assertNull(category);
    }

    @Test
    public void findById_CategoryFound() {
        Category category = subj.findById(1L).orElse(null);
        assertNotNull(category);
        assertEquals(Long.valueOf(1L), category.getId());
        assertEquals("TransCategoryName", category.getCategory());
        assertEquals(Long.valueOf(1L), category.getUser().getId());
    }
}