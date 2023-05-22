import java.lang.reflect.Proxy;
import java.sql.Connection;

class DAOGenerator {

    public static String className;
    public static <T> T generateDAO(Class<T> daoInterface, Connection connection) {
        className = daoInterface.getTypeName();
        return (T) Proxy.newProxyInstance(daoInterface.getClassLoader(), new Class[]{daoInterface}, new DAOInvocationHandler(connection));
    }
}

/*

 The DAOInvocationHandler is responsible for handling the method invocations on the DAO interface.

Next, it gets the ClassLoader of the DAO interface and creates an array of Class<?> containing the daoInterface itself.
 This is required for creating the proxy instance.

Finally, it uses the Proxy.newProxyInstance method to create a dynamic proxy instance of the DAO interface using the provided
 class loader, interfaces, and the invocation handler. The proxy instance is then cast to the DAO interface type and returned.

The generated DAO implementation will override the methods defined in the DAO interface and delegate the method calls to the
 DAOInvocationHandler, which handles the database operations using the provided connection.

This class enables decoupling the DAO implementation from the application code, allowing for dynamic execution of database queries
based on the method invocations defined in the DAO interface.
 */