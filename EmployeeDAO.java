import java.sql.SQLException;
import java.util.List;

interface EmployeeDAO {

    List<Employee> findAll();

    List<Employee> findBySSN(String ssn);

    List<Employee> findByNameAndSurname(String name, String surname);

    List<Employee> findBySalaryAndAge(double salary, int age);

    void deleteByNameAndSurname(String name, String surname);

    void deleteAll() throws SQLException;

    void deleteBySSN(String ssn) throws SQLException;

}
