package Model;

//import Model.ConnectionFactory;

import java.io.IOException;
import java.sql.*;

public class Employee {


    public static void main(String[] args) {






    public int employer_ID;
    public String prefix;
    public String firstName;
    public char middleInitial;
    public String lastName;
    public String email;
    public java.sql.Date dateOfBirth;
    public char gender;
    public java.sql.Date dateOfJoining;
    public int salary;





    @Override
    public String toString() {
        return "EmployeeRecord{" +
                "employer_ID=" + employer_ID +
                ", prefix='" + prefix + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleInitial=" + middleInitial +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", gender=" + gender +
                ", dateOfJoining=" + dateOfJoining +
                ", salary=" + salary +
                '}';

    }


    }
    }
