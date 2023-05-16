package com.llqlv.springcourse.dao;

import com.llqlv.springcourse.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    List<User> getAll();

    Optional<User> getPersonById(int id);

    void save(User user);

    void update(int id, User updatedUser);

    void delete(int id);

    Optional<User> getPersonByEmail(String email);
}
