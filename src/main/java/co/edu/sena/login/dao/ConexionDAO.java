package co.edu.sena.login.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** Establece una conexión JDBC con TiDB usando secretos del entorno de Render. */
public final class ConexionDAO {
    private ConexionDAO() { }

    /** Devuelve una conexión nueva; el llamador debe cerrarla con try-with-resources. */
    public static Connection getConexion() throws SQLException {
        String url = System.getenv("DB_URL");
        String user = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");
        if (isEmpty(url) || isEmpty(user) || isEmpty(password)) {
            throw new SQLException("Faltan las variables DB_URL, DB_USER o DB_PASSWORD.");
        }
        if (!url.startsWith("jdbc:mysql://")) {
            throw new SQLException("DB_URL debe utilizar el esquema jdbc:mysql://");
        }
        return DriverManager.getConnection(url, user, password);
    }

    private static boolean isEmpty(String value) {
        return value == null || value.isBlank();
    }
}
