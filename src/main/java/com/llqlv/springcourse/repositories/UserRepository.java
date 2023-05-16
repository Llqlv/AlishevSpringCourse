package com.llqlv.springcourse.repositories;

import com.llqlv.springcourse.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByName(String name);

    List<User> findByNameOrderByAge(String name);

    List<User> findByNameStartingWith(String startingWith);

    List<User> findByNameOrEmail(String name, String email);
}
