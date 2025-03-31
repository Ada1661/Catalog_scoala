package Administrare;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// JDBC = Java Data Base Connectivity
public class MyJDBC {
    private static MyJDBC instance;
    private Connection connection;
    private String url = "jdbc:mysql://localhost:3306/catalog";
    private String username = "root"; // utilizator cu toate priviligiile
    private String password = "LoKiJ_89.UHY";


    private MyJDBC() {
        try {
            connection = DriverManager.getConnection(url, username, password);
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public static synchronized MyJDBC getInstance() {
        if (instance == null) {
            instance = new MyJDBC();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
