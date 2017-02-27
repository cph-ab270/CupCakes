package model;

/**
 * Created by adam on 26/02/2017.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector implements hyggedb.Connector {

    private Connection conn = null;

    private static final String IP = "localhost";
    private static final int PORT = 3306;
    private static final String DATABASE = "cba_cupcake";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";


    public Connector(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            String url = "jdbc:mysql://" + IP + ":" + PORT + "/" + DATABASE;
            this.conn = DriverManager.getConnection(url, USERNAME, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return this.conn;
    }

    public void close() {
        try {
            getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

