package Administrare;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// design pattern
public class SingletonBD {
    private static SingletonBD instance;
    private Connection connection;
    public Connection getConnection() {
        return connection;
    }

    // constructor privat care previne instantierea clasei din afara acesteia
    private SingletonBD() {
    }

    // metoda statica care returneaza instanta unica a clasei
    public static synchronized SingletonBD getInstance() {
        // vericam daca instanta este deja creata
        if (instance == null) {
            instance = new SingletonBD();
        }
        return instance;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean execUpdate(String query) {
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        }
        catch (SQLException exception) {
            exception.printStackTrace();
            return false;
        }
    }

    public ResultSet execQuery(String query) {
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            return statement.executeQuery();
        }
        catch (SQLException exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
