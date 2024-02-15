/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template

 */
package JFrame;

import com.mysql.cj.log.ProfilerEvent;
import com.mysql.cj.util.SaslPrep;
import java.security.interfaces.RSAKey;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;

/**
 *
 * @author PRAMOD MADHUBHASHANA
 */
public class DBConnector {

    private Connection connection;
    String connectionString = "jdbc:mysql://localhost:3306/library";
    String userName = "root";
    String password = "";
    int studentid;

    public DBConnector() throws SQLException {
        connect();
    }

    public void connect() throws SQLException {
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        this.connection = DriverManager.getConnection(connectionString, userName, password);
    }

    public int SignUP(String userName, String password, String email, String contact) throws SQLException {

        StringBuilder sql = new StringBuilder("INSERT INTO admin(id,name,password,email,contact)");
        sql.append("VALUES(?,?,?,?,?)");

        PreparedStatement ps = connection.prepareStatement(sql.toString());
        ps.setString(1, null);
        ps.setString(2, userName);
        ps.setString(3, password);
        ps.setString(4, email);
        ps.setString(5, password);

        int status = ps.executeUpdate();
        return status;
    }

    public boolean Loging(String email, String password) throws SQLException {

        StringBuilder stringBuilder = new StringBuilder("SELECT * FROM admin WHERE email = ? AND password = ?");
        PreparedStatement preparedStatement = connection.prepareStatement(stringBuilder.toString());
        preparedStatement.setString(1, email);
        preparedStatement.setString(2, password);

        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    public boolean CheckDublicate(String email) throws SQLException {
        boolean rs;
        StringBuilder stringBuilder = new StringBuilder("SELECT * FROM admin WHERE email = ?");
        PreparedStatement preparedStatement = connection.prepareStatement(stringBuilder.toString());
        preparedStatement.setString(1, email);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            rs = true;
        } else {
            rs = false;
        }
        return rs;
    }

    public String GetName(String email) throws SQLException {

        String name = null;

        StringBuilder stringBuilder = new StringBuilder("SELECT name FROM admin WHERE email = ?");
        PreparedStatement preparedStatement = connection.prepareStatement(stringBuilder.toString());
        preparedStatement.setString(1, email);
        ResultSet rs = preparedStatement.executeQuery();

        if (rs.next()) {
            name = rs.getString("name");
        }
        return name;
    }

    public Object[][] BookIssueTable() throws SQLException {

        StringBuilder stringBuilder = new StringBuilder("SELECT * FROM bookissue");
        PreparedStatement preparedStatement = connection.prepareStatement(stringBuilder.toString());
        ResultSet resultSet = preparedStatement.executeQuery();

        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        int rows = NOofIssuedBook();

        Object[][] data = new Object[rows][columnCount];
        int row = 0;
        while (resultSet.next()) {
            for (int i = 1; i <= columnCount; i++) {
                data[row][i - 1] = resultSet.getObject(i);
            }
            row++;
        }
        return data;
    }

    public  Object[][] StudentDetails() throws SQLException{
      StringBuilder stringBuilder = new StringBuilder("SELECT * FROM bookissue");
        PreparedStatement preparedStatement = connection.prepareStatement(stringBuilder.toString());
        ResultSet resultSet = preparedStatement.executeQuery();

        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        int rows = NOofIssuedBook();

        Object[][] data = new Object[rows][columnCount];
        int row = 0;
        while (resultSet.next()) {
            for (int i = 1; i <= columnCount; i++) {
                data[row][i - 1] = resultSet.getObject(i);
            }
            row++;
        }
        return data;  
    }

    public Object[][] BookDetais() throws SQLException {

        StringBuilder stringBuilder = new StringBuilder("SELECT * FROM book");
        PreparedStatement preparedStatement = connection.prepareStatement(stringBuilder.toString());
        ResultSet resultSet = preparedStatement.executeQuery();

        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        int rows = NOofIssuedBook();

        Object[][] data = new Object[rows][columnCount];
        int row = 0;
        while (resultSet.next()) {
            for (int i = 1; i <= columnCount; i++) {
                data[row][i - 1] = resultSet.getObject(i);
            }
            row++;
        }
        return data;
    }

    public void StudentId(String email) throws SQLException {

        String name = null;

        StringBuilder stringBuilder = new StringBuilder("SELECT studentId FROM student WHERE email = ?");
        PreparedStatement preparedStatement = connection.prepareStatement(stringBuilder.toString());
        preparedStatement.setString(1, email);

        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            studentid = rs.getInt("studentId");
        }
    }

    public int NOofBooks() throws SQLException {

        int book = 0;
        StringBuilder stringBuilder = new StringBuilder("SELECT COUNT(*) FROM book");
        PreparedStatement preparedStatement = connection.prepareStatement(stringBuilder.toString());
        ResultSet rs = preparedStatement.executeQuery();

        if (rs.next()) {
            book = rs.getInt(1);
        }

        return book;
    }

    public int NoofStudent() throws SQLException {
        int student = 0;
        StringBuilder stringBuilder = new StringBuilder("SELECT COUNT(*) FROM student");
        PreparedStatement preparedStatement = connection.prepareStatement(stringBuilder.toString());
        try (ResultSet rs = preparedStatement.executeQuery()) {
            if (rs.next()) {
                student = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return student;
    }

    public int NOofIssuedBook() throws SQLException {

        int issuedBook = 0;
        StringBuilder stringBuilder = new StringBuilder("SELECT COUNT(*) FROM bookissue");
        PreparedStatement preparedStatement = connection.prepareStatement(stringBuilder.toString());
        try (ResultSet rs = preparedStatement.executeQuery()) {
            if (rs.next()) {
                issuedBook = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return issuedBook;
    }

    public int BookRelease(String bookid) throws SQLException {
        StringBuilder stringBuilder = new StringBuilder("DELETE FROM bookissue WHERE bookId = ?");
        PreparedStatement preparedStatement = connection.prepareStatement(stringBuilder.toString());
        preparedStatement.setString(1, bookid);
        int status = preparedStatement.executeUpdate();
        return status;
    }

    public int IssueBook(String bookid, String studentid, String returndate) throws SQLException {
        StringBuilder sql = new StringBuilder("INSERT INTO bookissue(bookId,date,returnDate,email,studentID)");
        sql.append("VALUES(?,?,?,?,?)");
        Date curreDate = new Date();
        PreparedStatement ps = connection.prepareStatement(sql.toString());
        ps.setString(1, bookid);
        ps.setString(2, curreDate.toString());
        ps.setString(3, returndate);
        ps.setString(4, studentid);
        ps.setString(5, password);

        int status = ps.executeUpdate();
        return status;

    }

    public int AdddBook(String bookid, String bookName, String auother, String date, String quantity) throws SQLException {
        StringBuilder sql = new StringBuilder("INSERT INTO book(bookID,name,auother,date,quantity)");
        sql.append("VALUES(?,?,?,?,?)");

        PreparedStatement ps = connection.prepareStatement(sql.toString());
        ps.setString(1, bookid);
        ps.setString(2, bookName);
        ps.setString(3, auother);
        ps.setString(4, date);
        ps.setString(5, quantity);

        int status = ps.executeUpdate();
        return status;
    }

    public int RemoveBook(String bookid) throws SQLException {
        StringBuilder stringBuilder = new StringBuilder("DELETE FROM book WHERE bookID = ?");
        PreparedStatement preparedStatement = connection.prepareStatement(stringBuilder.toString());
        preparedStatement.setString(1, bookid);
        int status = preparedStatement.executeUpdate();
        return status;
    }

    public String ChangeBookDeatails(String bookid) throws SQLException {

        StringBuilder stringBuilder = new StringBuilder("SELECT * FROM book WHERE bookID =?");
        PreparedStatement preparedStatement = connection.prepareStatement(stringBuilder.toString());
        preparedStatement.setString(1, bookid);
        ResultSet rs = preparedStatement.executeQuery();
        String returnstring = null;
        if (rs.next()) {
            int book_id = rs.getInt("bookID");
            String name = rs.getString("name");
            String auth = rs.getString("auother");
            String d = rs.getString("date");
            int qua = rs.getInt("quantity");

            returnstring = book_id + "|" + name + "|" + auth + "|" + d + "|" + qua;
        } else {
            returnstring = "0";
        }
        return connectionString;
    }

    public int EditBookDetails(String name, String auother, String date, String quantity, String bookid) throws SQLException {

        StringBuilder sql = new StringBuilder("UPDATE book SET name = ?, author = ?, date = ?, quantity = ? WHERE bookid = ?");
        sql.append("VALUES(?,?,?,?,?)");

        PreparedStatement ps = connection.prepareStatement(sql.toString());
        ps.setString(1, name);
        ps.setString(2, auother);
        ps.setString(3, date);
        ps.setString(4, quantity);
        ps.setString(5, bookid);

        int status = ps.executeUpdate();
        return status;
    }

}
