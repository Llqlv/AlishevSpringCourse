package com.llqlv.springcourse.dao;

import com.llqlv.springcourse.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    // ------------------------------------------------------------
    //                 Batch Update vs Multiple update
    //                 Тестируем производительность пакетной вставки
    // ------------------------------------------------------------
    public void testMultipleUpdate() {
        List<User> users = create1000people();

        long before = System.currentTimeMillis();

        users.forEach(user -> save(user));

        long after = System.currentTimeMillis();
        System.out.println("Time: " + (after - before));
    }

    

    private List<User> create1000people() {
//        List<User> users = new ArrayList<>();
//
//        for (int i = 0; i < 1000; i++) {
//            users.add(new User(i, "Name" + i, 11, "test" + i + "@mail.ru"));
//        }
//
//        return users;
        return IntStream.range(0, 1000).mapToObj(i -> new User(i, "Name" + i, 11, "test" + i + "@mail.ru")).collect(Collectors.toList());
    }

    public void testBatchUpdate() {
        var users = create1000people();

        long before = System.currentTimeMillis();

        jdbcTemplate.batchUpdate("INSERT INTO users VALUES (?, ?, ?, ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setInt(1, users.get(i).getId());
                        ps.setString(2, users.get(i).getName());
                        ps.setInt(3, users.get(i).getAge());
                        ps.setString(4, users.get(i).getEmail());
                    }

                    @Override
                    public int getBatchSize() {
                        return users.size();
                    }
                });

        long after = System.currentTimeMillis();
        System.out.println("Time: " + (after - before));
    }
}
