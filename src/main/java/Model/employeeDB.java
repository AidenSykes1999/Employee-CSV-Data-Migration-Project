package model;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class EmployeeDB {
    public static void main(String[] args) {

        createDatabase();

        Date dob = new Date(02 / 04 / 1998);
        Date join = new Date(03 / 07 / 2003);
        insertEmployee(123, "Mr", "Bob", 'F', "Smith", 'M',
                "bob@gmail.com", dob, join, 120000);

        selectAllRecords();

    }

    public static void createDatabase(){
        Statement statement = null;
        try {
            Connection connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            statement.execute("DROP DATABASE IF EXISTS EmployeeCSV");
            statement.execute("CREATE DATABASE EmployeeCSV");
            statement.execute("USE EmployeeCSV");
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

    public static void selectAllRecords() {
        Statement statement = null;
        ResultSet rs = null;
        try {
            Connection connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM Employee;");
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

    public static void insertEmployeesThreaded(ArrayList<Employee> employees, int threadCount){
        ArrayList<Thread> employeeInsertThreads = new ArrayList<>();
        for(int i = 0; i < threadCount; i++){
            Thread thread = new Thread(new EmployeeDataInsertThread(employees, i, threadCount));
            employeeInsertThreads.add(thread);
        }
        for(int i = 0; i < threadCount; i++){
            employeeInsertThreads.get(i).start();
        }

        try {
            ConnectionFactory.getConnection().close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < threadCount; i++){
            try {
                employeeInsertThreads.get(i).join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

//    public static void insertEmployeeThreaded(int employeeId, String namePrefix, String firstName, char middleInitial,
//                                      String lastName, char gender, String email, Date dateOfBirth, Date dateOfJoining, int salary) {
//
//        PreparedStatement preparedStatement = null;
//        Statement statement = null;
//        try {
//            Connection connection = ConnectionFactory.getConnection();
//            statement = connection.createStatement();
//            preparedStatement = connection.prepareStatement("INSERT INTO Employee (Emp_ID, Name_Prefix, First_Name, Middle_Initial, Last_Name, Gender, Email, Date_Of_Birth, Date_Of_Joining, Salary) VALUES (?,?,?,?,?,?,?,?,?,?)");
//            preparedStatement.setString(1, ""+employeeId);
//            preparedStatement.setString(2, namePrefix);
//            preparedStatement.setString(3, firstName);
//            preparedStatement.setString(4, ""+middleInitial);
//            preparedStatement.setString(5, lastName);
//            preparedStatement.setString(6, ""+gender);
//            preparedStatement.setString(7, email);
//            preparedStatement.setString(8, ""+dateOfBirth);
//            preparedStatement.setString(9, ""+dateOfJoining);
//            preparedStatement.setString(10, ""+salary);
//
//            int rowsAffected = preparedStatement.executeUpdate();
//            preparedStatement.close();
//        } catch (SQLException | IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                ConnectionFactory.closeConnection();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    public static void insertEmployee(int employeeId, String namePrefix, String firstName, char middleInitial,
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
}