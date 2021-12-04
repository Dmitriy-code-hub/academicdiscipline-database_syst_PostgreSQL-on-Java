package sample;

import java.sql.*;
import java.util.Scanner;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class BDConnector {
    static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/budjet";
    static final String USER = "postgres";
    static final String PASS = "1979Dmitriy";
    public static Connection connection;

    BDConnector(){
        if (connection == null) Connect();
    }

    public void Connect()
    {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path ");
            e.printStackTrace();
            return;
        }
        //System.out.println("PostgreSQL JDBC Driver successfully connected");
        try {
            connection = DriverManager
                    .getConnection(DB_URL, USER, PASS);

        } catch (SQLException e) {
            System.out.println("Connection Failed");
            e.printStackTrace();
            return;
        }
        /*
        if (connection != null) {
            System.out.println("You successfully connected to database now");
        } else {
            System.out.println("Failed to make connection to database");
            return;
        }
        */
    }
    public void DisConnect()
    {
        try {
            if (connection != null) connection.close();
        }catch (SQLException e) {
            System.out.println("connection.close() Failed");
            e.printStackTrace();
        }
    }
}
