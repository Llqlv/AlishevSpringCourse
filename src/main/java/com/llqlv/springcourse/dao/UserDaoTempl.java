package com.llqlv.springcourse.dao;

import com.llqlv.springcourse.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDaoTempl {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDaoTempl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //Использование собственного RowMapper'а -> перевод строки в объект
    /*public List<User> getAll() {
        return jdbcTemplate.query("SELECT * FROM users", new UserMapper());
    }*/

    public List<User> getAll() {
        return jdbcTemplate.query("SELECT * FROM users", new BeanPropertyRowMapper<>(User.class));
    }

    public User getPersonById(int id) {
        return jdbcTemplate.query("SELECT * FROM users WHERE id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(User.class))
                .stream().findAny().orElse(null);
    }

    public void save(User user) {
        jdbcTemplate.update("""
                INSERT INTO users(id, name, age, email) VALUES (1, ?, ?, ?)
                """, user.getName(), user.getAge(), user.getEmail());
    }

    public void update(int id, User updatedUser) {
        jdbcTemplate.update("""
                UPDATE users SET
                name=?, age=?, email=?
                WHERE id=?
                """,
                updatedUser.getName(), updatedUser.getAge(), updatedUser.getEmail(), updatedUser.getId());
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM users WHERE id=?", id);
    }

}
