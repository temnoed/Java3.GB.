package ru.cdv.Lesson2;

import java.sql.*;

/**
 * Дз урок 2. Java 3. GeekBrains.
 */
public class MainLesson2 {
    private static Connection connection;
    private static Statement stmt;
    private static PreparedStatement ps;


    public static void main(String[] args) {
        try {

            // Создаём таблицу
            connect();
            dropTableEx();
            createTableEx();

            // Заполняем 10000 строк
            fill10000strings();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
    }


    private static void rollbackEx() throws SQLException {
        connection.setAutoCommit(false);
        stmt.executeUpdate("INSERT INTO students (name, score) " +
                "VALUES ('bob1', 10);");
        Savepoint sp = connection.setSavepoint();
        stmt.executeUpdate("INSERT INTO students (name, score) " +
                "VALUES ('bob2', 20);");
        connection.rollback(sp);
        stmt.executeUpdate("INSERT INTO students (name, score) " +
                "VALUES ('bob3', 30);");
        connection.commit();
    }


    private static void fill10000strings() throws SQLException {
        ps = connection.prepareStatement(
                "INSERT INTO goods ('prodid', 'title', 'cost') " +
                        "VALUES (?, ?, ?);");
        long t = System.currentTimeMillis();
        connection.setAutoCommit(false);
        for (int i = 1; i < 10000; i++) {
            ps.setString(1, "id_товара " + i);
            ps.setString(2, "товар" + i);
            ps.setFloat(3, (i * 10));
            ps.addBatch();
        }
        ps.executeBatch();
        connection.commit();
        System.out.println(System.currentTimeMillis() - t +" msec");
    }


    private static void preparedStatementSimpleEx() throws SQLException {
        ps = connection.prepareStatement("INSERT INTO students (name, score) VALUES (?, ?);");
        long t = System.currentTimeMillis();
        connection.setAutoCommit(false);
        for (int i = 0; i < 1000; i++) {
            ps.setInt(2, (i * 5) % 100);
            ps.setString(1, "bob" + i);
            ps.executeUpdate();
        }
        connection.commit();
        System.out.println(System.currentTimeMillis() - t);
    }


    private static void dropTableEx() throws SQLException {
        stmt.execute("DROP TABLE IF EXISTS goods");
    }


    private static void createTableEx() throws SQLException {
        String sqlStr = "CREATE TABLE IF NOT EXISTS goods (" +
                "'id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "'prodid' INTEGER NOT NULL UNIQUE, " +
                "'title' STRING UNIQUE NOT NULL, " +
                "'cost' REAL);";
        stmt.execute(sqlStr);
    }


    private static void selectEx() throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT * FROM students WHERE score > 50");
        ResultSetMetaData rsmd = rs.getMetaData();
        while (rs.next()) {
            System.out.println(rs.getInt(1) + " " + rs.getString("name") +
                    " " + rs.getInt(3));
        }
    }


    private static void generateEntriesEx() throws SQLException {
        for (int i = 1; i <= 10; i++) {
            stmt.executeUpdate(String.format("INSERT INTO students (name, score) " +
                    "VALUES ('%s', %d);", "bob" + i, i * 10));
        }
    }


    private static void updateEx() throws SQLException {
        stmt.executeUpdate("UPDATE students SET score = 100 WHERE name = 'bob3';");
    }


    private static void deleteEx() throws SQLException {
        stmt.executeUpdate("DELETE FROM students WHERE score < 30;");
    }


    private static void insertEx() throws SQLException {
        stmt.executeUpdate("INSERT INTO students (name, score) VALUES ('bob4', 40);");
    }


    private static void connect() throws Exception {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:main.db");
        stmt = connection.createStatement();
    }


    private static void disconnect() {
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