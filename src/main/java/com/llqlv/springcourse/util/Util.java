package com.llqlv.springcourse.util;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class Util {

    private static final String URL = "jdbc:postgresql://localhost:5432/alishev";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "root";


    //Статическая инициализации загрузки драйвера PostgreSQL
    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private Util() {}

    @Bean
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
