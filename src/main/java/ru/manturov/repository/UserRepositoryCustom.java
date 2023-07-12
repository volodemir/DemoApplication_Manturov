package ru.manturov.repository;

import org.springframework.stereotype.Repository;
import ru.manturov.entity.User;

import java.util.List;

@Repository
public interface UserRepositoryCustom {
    List<User> findByFilter(UserFilter userFilter);
}
