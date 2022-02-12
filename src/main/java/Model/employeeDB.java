package model;

import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;

public class employeeDB {
    public static void main(String[] args) {

//
//        useStatement();
//
//        usePreparedStatement();
//
//        insertPrepared();
        createDatabase();
        Date dob = new Date(02 / 04 / 1998);
        Date join = new Date(03 / 07 / 2003);
        insertEmployee(123, "Mr", "Bob", 'F', "Smith", 'M',
                "bob@gmail.com", dob, join, 120000);

        useStatement();

    }

    private static void createDatabase() {
        Statement statement = null;
        try {
            Connection connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            statement.execute("DROP DATABASE IF EXISTS EmployeeCSV");
            statement.execute("CREATE DATABASE EmployeeCSV");
            statement.execute("USE EmployeeCSV");
            //statement.execute("CREATE TABLE Employee(employee integer)");
            //statement.execute("CREATE TABLE Employee (int employeeId, varchar(10) namePrefix, varchar(100) firstName, char middleInitial, varchar(100) lastName, char gender, varchar(100) email, Date dateOfBirth, Date dateOfJoining, int salary)");
            statement.execute("CREATE TABLE IF NOT EXISTS Employee (" +
                    "Emp_ID integer PRIMARY KEY not null," +
                    "Name_Prefix varchar(10) not null," +
                    "First_Name varchar(100) not null," +
                    "Middle_Initial char not null," +
                    "Last_Name varchar (100) not null," +
                    "Gender char not null," +
                    "Email varchar (100) not null," +
                    "Date_Of_Birth Date not null," +
                    "Date_Of_Joining Date not null," +
                    "Salary integer not null)");

            statement.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                ConnectionFactory.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    private static void useStatement() {

        Statement statement = null;
        ResultSet rs = null;
        try {
            Connection connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            statement.execute("USE EmployeeCSV");
            rs = statement.executeQuery("SELECT * FROM Employee");
            while (rs.next()) { // Whilst there is a next element in the collection, the loop will keep running.
                System.out.println(rs.getString("first_name") + " " + rs.getString("last_name"));
            }
            rs.close();
            statement.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                ConnectionFactory.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    private static void insertEmployee(int employeeId, String namePrefix, String firstName, char middleInitial,
                                       String lastName, char gender, String email, Date dateOfBirth, Date dateOfJoining, int salary) {

        PreparedStatement preparedStatement = null;
        Statement statement = null;
        try {
            Connection connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            statement.execute("USE EmployeeCSV");
            preparedStatement = connection.prepareStatement("INSERT INTO Employee (Emp_ID, Name_Prefix, First_Name, Middle_Initial, Last_Name, Gender, Email, Date_Of_Birth, Date_Of_Joining, Salary) VALUES (?,?,?,?,?,?,?,?,?,?)");
            preparedStatement.setString(1, ""+employeeId);
            preparedStatement.setString(2, namePrefix);
            preparedStatement.setString(3, firstName);
            preparedStatement.setString(4, ""+middleInitial);
            preparedStatement.setString(5, lastName);
            preparedStatement.setString(6, ""+gender);
            preparedStatement.setString(7, email);
            preparedStatement.setString(8, ""+dateOfBirth);
            preparedStatement.setString(9, ""+dateOfJoining);
            preparedStatement.setString(10, ""+salary);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected);
            preparedStatement.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                ConnectionFactory.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }




    }


    private static void insertPrepared() {

        PreparedStatement preparedStatement = null;
        try {
            Connection connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO Employee (idemployee_id,first_name, last_name,email,date,time) VALUES (?,?,?,?,?,?)");
            preparedStatement.setString(1, "4");
            preparedStatement.setString(2, "Aiden");
            preparedStatement.setString(3, "Sykes");
            preparedStatement.setString(4, "aiden.sykes@gmail.com");
            preparedStatement.setString(5, "2021/01/11");
            preparedStatement.setString(6, "10.00.00");
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected);
            preparedStatement.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                ConnectionFactory.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }




    }

    private static void usePreparedStatement() {

        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            Connection connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM Employee WHERE last_name=?");
            preparedStatement.setString(1, "dhamale"); // Placeholders
            rs = preparedStatement.executeQuery();
            while (rs.next()) { // Whilst there is a next element in the collection, the loop will keep running.
                System.out.println(rs.getString("first_name") + " " + rs.getString("last_name"));
            }
            rs.close();
            preparedStatement.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                ConnectionFactory.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }




    }
}