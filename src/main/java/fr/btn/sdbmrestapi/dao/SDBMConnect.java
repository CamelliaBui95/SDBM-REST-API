package fr.btn.sdbmrestapi.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class SDBMConnect {
    private static Connection connection;

    private SDBMConnect() {

    }

    public static Connection getInstance() {
        if(connection == null) {
            try {
                String url = "jdbc:sqlserver://127.0.0.1:1433;databaseName=sdbm;encrypt=false";
                String user = "dev";
                String password = "abcd@1234";

                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                connection = DriverManager.getConnection(url, user, password);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        return connection;
    }
}
