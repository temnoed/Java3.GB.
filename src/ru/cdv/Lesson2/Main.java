package ru.cdv.Lesson2;

import java.sql.*;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    static Connection connection = null;
    static Statement stmt = null;
    static PreparedStatement ps = null;
    static String[] pieces;
    static String sqlStr, str;
    static Scanner sc = new Scanner(System.in);


    public static void main(String[] args) {

        do {
            System.out.println("Принимаем команду: /команда *** ***");
            str = sc.nextLine();
            // регулярное выражение можно оптимизировать:
        } while (!Pattern.compile("/[а-я]+ [A-Za-zА-Яа-я0-9 ]+$").matcher(str).matches());
        String[] pieces = str.split("\\s+");

        try {
            connect();
            String testStr = pieces[0];
            switch (testStr) {
                case "/цена":
                    getCostByName(pieces[1]);
                    break;
                case "/сменитьцену":
                    changePrice(pieces[1], new Integer(pieces[2]));
                    break;
                case "/товарыпоцене":
                    tovaryPoCene(new Integer(pieces[1]), new Integer(pieces[2]));
                    break;

                default:
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
    }

    static void getCostByName(String aName) throws SQLException {

        String sqlStr = "SELECT title, cost FROM goods where title = ?";
        ps = connection.prepareStatement(sqlStr);
        ps.setString(1, aName);

        connection.setAutoCommit(false);
        ResultSet rs = ps.executeQuery();
        connection.commit();

        if (rs.next()) {
            System.out.println(rs.getString("title") +
                    " цена:" + rs.getInt("cost"));
        } else {
            System.out.println("Такого товара у нас точно нет.");
        }

    }

    static void changePrice(String aTitle, int newPrice) throws SQLException {

        sqlStr = "UPDATE goods SET cost = ? WHERE title = ?";
        ps = connection.prepareStatement(sqlStr);
        ps.setInt(1, newPrice);
        ps.setString(2, aTitle);

        connection.setAutoCommit(false);
        int result = ps.executeUpdate();
        connection.commit();

        if (result != 0) {
            System.out.println("Цена товара изменена.");
        } else {
            System.out.println("Запрос замены цены не прошёл.");
        }
    }

    static void tovaryPoCene(int priceFrom, int priceTo) throws SQLException {

        sqlStr = "SELECT * FROM goods where cost BETWEEN ? and  ?";
        ps = connection.prepareStatement(sqlStr);
        ps.setInt(1, priceFrom);
        ps.setInt(2, priceTo);

        connection.setAutoCommit(false);
        ResultSet rs = ps.executeQuery();
        connection.commit();

        if (rs.next())
            do {
                System.out.println(rs.getString("title") +
                        " цена:" + rs.getInt("cost"));
            } while (rs.next());
        else {
            System.out.println("Таких цен у нас нет.");
        }
    }


    static void connect() throws Exception {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:main.db");
        stmt = connection.createStatement();
    }


    static void disconnect() {
        try {
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}