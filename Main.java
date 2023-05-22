import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
Create the Entity/DTO Class

    Ask the user to provide the Java class representing the entity or data transfer object.
    Parse the class to extract its attributes' names and types.
    Create a Java class file for the entity, if it doesn't already exist, using the provided class name.

Create the DAO Interface

    Ask the user to provide the Java interface for the DAO.
    Parse the interface to extract its method names and argument types.
    Create a Java interface file for the DAO, if it doesn't already exist, using the provided interface name.

Generate the DAO Implementation

    Read the provided connection URL to establish a JDBC connection to the database.
    Create a Java class file for the DAO implementation, with the name <DAOInterfaceName>Impl, if it doesn't already exist.
    Implement the methods in the DAO implementation class by parsing the method names and constructing the corresponding queries.
    Execute the queries using JDBC, passing the provided attribute values as parameters.
    Map the retrieved data to instances of the entity class and return them as lists.
 */

// Data Transfer Object (DTO) classes

// DAO interface

// DAO generator

// DAO invocation handler

// Usage examples

public class Main {

    static Connection connection;
    public static void main(String[] args) throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dao_database", "root", "1458");

        BookDAO bookDAO = DAOGenerator.generateDAO(BookDAO.class, Main.connection);
        List<Book> booksByTitle = bookDAO.findByTitle("The Great Gatsby");
        List<Book> booksByAuthor = bookDAO.findByAuthor("J.K. Rowling");
        List<Book> booksByYearAndPrice = bookDAO.findByYearAndPrice(1925, 19.99);

        System.out.println("---- Books ----");

        System.out.println(booksByTitle);
        System.out.println(booksByAuthor);
        System.out.println(booksByYearAndPrice);

//        System.out.println("Delete by title:");
//        bookDAO.deleteByTitle("The Great Gatsby");


        EmployeeDAO employeeDAO = DAOGenerator.generateDAO(EmployeeDAO.class, Main.connection);
        List<Employee> employeesBySSN = employeeDAO.findBySSN("456789123");
        List<Employee> employeesByNameAndSurname = employeeDAO.findByNameAndSurname("Emily", "Davis");
        List<Employee> employeesBySalaryAndAge = employeeDAO.findBySalaryAndAge(6000.0, 35);
        List<Employee> allEmployees = employeeDAO.findAll();

        System.out.println("---- Employee ----");
        System.out.println(allEmployees);
        System.out.println(employeesBySSN);
        System.out.println(employeesByNameAndSurname);
        System.out.println(employeesBySalaryAndAge);
//        System.out.println("Delete by SSN:");
//        employeeDAO.deleteBySSN(String.valueOf(123456789));
//        System.out.println("After: ");
//        System.out.println(employeeDAO.findAll());
//
//        System.out.println("Delete by name and surname:");
//        employeeDAO.deleteByNameAndSurname("Jane", "Smith");

        System.out.println("After: ");
        System.out.println(employeeDAO.findAll());
        connection.close();
    }
}
