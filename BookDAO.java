import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

interface BookDAO {
    List<Book> findByTitle(String title ) throws SQLException;

    List<Book> findByAuthor(String author) throws SQLException;

    List<Book> findByYearAndPrice(int year, double price ) throws SQLException;

    void deleteAll() throws SQLException;

    void deleteByTitle(String author) throws SQLException;
    void deleteByYearAndPrice(String author) throws SQLException;

}
