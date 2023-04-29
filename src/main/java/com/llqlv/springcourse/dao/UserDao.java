package com.llqlv.springcourse.dao;

import com.llqlv.springcourse.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserDao {
    private static int COUNT;

    private final Connection connection;

    @Autowired
    public UserDao(Connection connection) {
        this.connection = connection;
    }

    public List<User> getAll() {
        final String SQL_QUERY = "SELECT * FROM users";

        List<User> userList;

        try (Statement statement = connection.createStatement()) {
            var resultSet = statement.executeQuery(SQL_QUERY);

            userList = createUserList(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return userList;
    }

    public User getPersonById(int id) {
        String SQL_QUERY = """
                SELECT * FROM users
                WHERE id = ?
                """;
        User user;

        try (var preparedStatement = connection.prepareStatement(SQL_QUERY)) {
            preparedStatement.setInt(1, id);
            var resultSet = preparedStatement.executeQuery();
            resultSet.next();

            user = createUserFromResultSet(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return user;
    }

    public void save(User user) {
        String SQL_QUERY = """
                insert into users (id, name, age, email) 
                VALUES (?, ?, ?, ?)
                """;

        try (var statement = connection.prepareStatement(SQL_QUERY)) {
            statement.setInt(1, ++COUNT);
            statement.setString(2, user.getName());
            statement.setInt(3, user.getAge());
            statement.setString(4, user.getEmail());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(int id, User updatedUser) {
        String SQL_QUERY = """
                UPDATE users SET
                name=?, age=?, email = ?
                WHERE id=?
                """;
        try (var preparedStatement = connection.prepareStatement(SQL_QUERY)) {
            preparedStatement.setString(1, updatedUser.getName());
            preparedStatement.setInt(2, updatedUser.getAge());
            preparedStatement.setString(3, updatedUser.getEmail());
            preparedStatement.setInt(4, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void delete(int id) {
        String SQL_QUERY = """
                DELETE FROM users WHERE id = ?
                """;

        try (var preparedStatement = connection.prepareStatement(SQL_QUERY)) {
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

// Вспомогательные методы для создания из ResultSet

    private List<User> createUserList(ResultSet resultSet) throws SQLException {
        List<User> users = new ArrayList<>();

        while (resultSet.next()) {
            User user = createUserFromResultSet(resultSet);

            users.add(user);
        }

        return users;
    }

    private User createUserFromResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setName(resultSet.getString("name"));
        user.setAge(resultSet.getInt("age"));
        user.setEmail(resultSet.getString("email"));
        return user;
    }

}
