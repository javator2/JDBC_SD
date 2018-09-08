package com.sda.jdbc;


import sun.java2d.pipe.SpanShapeRenderer;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static javafx.scene.input.KeyCode.L;

public class Main {

    public static final String URL
            = "jdbc:mysql://localhost/ksiegarnia?useSSL=false&serverTimezone=UTC";

    private static final  String username = "root";
    public static final String password = "Sebastiano1989";

    public static void main(String[] args) throws ParseException {

        Connection connection = null; // polaczenie z baza
        Statement statement = null; // wykonanie zapytania

        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // rejestracja sterownikow do pamieci
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(URL, username, password);
            String sql = "select * from books";
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()){
                int pageCount = resultSet.getInt("page_count");
                String title = resultSet.getString("title");

                System.out.println(title + " " + pageCount);
            }

           /* String sqlInsert = "INSERT INTO books (title, author, isbn, category, page_count, publisher, price) VALUES (" +
                    "'Java Programowanie praktyczne od podstaw'," +
                    "'Krzysztof Barteczko'," +
                    "'978-83-011-7845-5'," +
                    "'programowanie java'," +
                    "567," +
                    "'PWN'," +
                    "60.00" +
                    "); ";
            statement.executeUpdate(sqlInsert);
*/
            String sqlDelete = "DELETE FROM books WHERE isbn = '978-83-011-7845-5';";
            statement.executeUpdate(sqlDelete);

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime now = LocalDateTime.now();
            java.util.Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dtf.format(now));

            java.util.Date sqlDate = new java.sql.Date(date.getTime());

            PreparedStatement statement1 = connection.prepareStatement(
                    "INSERT INTO books values (?,?,?,?,?,?,?,?,?)"
            );
            statement1.setString(1,"Tytuł nowej ksiazki");
            statement1.setString(2,"autor");
            statement1.setDate(3, new Date(2323));
            statement1.setString(4, "isbn");
            statement1.setString(5, "kategoria");
            statement1.setInt(6, 22);
            statement1.setString(7, "33");
            statement1.setInt(8, 222);
            statement1.setInt(9, 1);

            statement1.execute();

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
