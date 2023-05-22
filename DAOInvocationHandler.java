import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

class DAOInvocationHandler implements InvocationHandler {
    private Connection connection;

    public DAOInvocationHandler(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        String methodName = method.getName();
        String[] parts = methodName.split("(?<=[a-z])(?=[A-Z])"); // Split camel case method name

        String tableName = DAOGenerator.className.split("(?<=[a-z])(?=[A-Z])")[0].toLowerCase();

//        System.out.println("parts "+ Arrays.toString(parts));

        String queryType = parts[0];
        String allBy = parts[1];
        String[] queryAttributes = new String[parts.length - 1];
        for (int i = 1; i < parts.length; i++) {
            queryAttributes[i - 1] = parts[i].toLowerCase();
        }

        String sql = generateSQL(queryType, allBy, tableName, queryAttributes);
//        System.out.println("sql:: "+sql);

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            setStatementParameters(statement, args);

            if(queryType.contains("delete")){
                statement.executeUpdate();
                return null;

            }
            ResultSet resultSet = statement.executeQuery();

            List<Object> results = new ArrayList<>();

            while (resultSet.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    row.put(resultSet.getMetaData().getColumnLabel(i), resultSet.getObject(i));
                }
                results.add(row);

            }

            return results;
        }
    }

    private String generateSQL(String queryType, String allBy, String tableName, String[] queryAttributes) {
        StringBuilder sql;

//        System.out.println("q type: "+queryType);

        if (queryType.startsWith("find")) {

            sql = new StringBuilder("SELECT * FROM ");
            sql.append(tableName);

            if(allBy.startsWith("All")){
                return sql.toString(); // string already constructed;
            }

            sql.append(" WHERE ");

        for (int i = 1; i < queryAttributes.length; i++) {
                if (i > 1) {
                        sql.append(" AND ");
                        sql.append(queryAttributes[i+1]).append(" = ?");
                        i++;
                    }
                    else
                        sql.append(queryAttributes[i]).append(" = ?");
                }

            return sql.toString();

        } else if (queryType.startsWith("delete")) {


            sql = new StringBuilder("DELETE FROM ");
            sql.append(tableName);

            if(allBy.startsWith("All")){
                return sql.toString(); // string already constructed;
            }

            sql.append(" WHERE ");

            for (int i = 1; i < queryAttributes.length; i++) {
                if (i > 1) {
                    sql.append(" AND ");
                    sql.append(queryAttributes[i+1]).append(" = ?");
                    i++;
                }
                else
                    sql.append(queryAttributes[i]).append(" = ?");
            }
            return sql.toString();

        }

        return null;

    }

    private void setStatementParameters(PreparedStatement statement, Object[] args) throws SQLException {
        if (args != null) {
//            System.out.println("args "+Arrays.toString(args));

                for (int i = 0; i < args.length; i++) {
                    statement.setObject(i + 1, args[i]);
                }

        }
    }
}
