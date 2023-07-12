package ru.manturov.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.manturov.entity.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RegServiceTest {

    @Mock
    RegService regService;

    @Test
    public void registration_EmptyPassword() {
        when(regService.registration("va@ya.ru", "")).thenReturn(null);
        assertNull(regService.registration("va@ya.ru", ""));
        verify(regService, times(1)).registration("va@ya.ru", "");
    }

    @Test
    public void registration_Registered() {
        User user = new User();
        user.setEmail("va@ya.ru");
        user.setPassword("hex");

        when(regService.registration("va@ya.ru", "hex")).thenReturn(user);

        assertEquals(regService.registration("va@ya.ru", "hex"), user);
        verify(regService, times(1)).registration("va@ya.ru", "hex");
    }
}
