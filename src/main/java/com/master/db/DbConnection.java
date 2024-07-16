package com.master.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

    //Connection для подключения из любого метода к классе
    private static Connection connection;
    private static String dbName = "pdf_files";
    private static String dbUser = "root";
    private static String dbPass = "1234";



    public static Connection getConnection() {
        System.out.println("Connecting DB");

            System.out.println("Connecting DB2");
            try {

                //создали связь с БД
                connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/" + dbName +
                                "?user=" + dbUser + "&password=" + dbPass);

                System.out.println("Connecting DB3");

                connection.createStatement().execute("DROP TABLE IF EXISTS pdf_info");
                connection.createStatement().execute("CREATE TABLE pdf_info(" +
                        "id INT NOT NULL AUTO_INCREMENT, " +
                        "date VARCHAR(250) NOT NULL, " +
                        "filename VARCHAR(250) NOT NULL, " +
                        "path TEXT NOT NULL, " +
                        "pagesCount INT NOT NULL, " +
                        "username VARCHAR(250) NOT NULL, " +
                        "PRIMARY KEY(id))");

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        return connection;
    }



    //выполнить множественный INSERT
    public boolean executeMultiInsert(String sqlStringBuilder) throws SQLException {

        System.out.println("insert method");
        String sql = "INSERT INTO pdf_info (date, filename, path, pagesCount, username) " +
                "VALUES " + sqlStringBuilder;
        DbConnection.getConnection().prepareStatement(sql).execute();

        return true;

    }
}
