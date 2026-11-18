package co.edu.sena.login.dao;

import co.edu.sena.login.modelo.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** DAO: concentra las operaciones SQL de usuarios, separado de los Servlets. */
public final class UsuarioDAO {

    /** Resultado interno de la consulta de autenticación; el hash no va a la sesión. */
    public record RegistroAutenticacion(Usuario usuario, String hashContrasena) { }

    /** Busca por usuario mediante sentencia parametrizada para evitar inyección SQL. */
    public RegistroAutenticacion buscarPorUsuario(String nombreUsuario) throws SQLException {
        String sql = "SELECT id, nombre, usuario, correo, rol, password_hash "
                + "FROM usuarios WHERE usuario = ? LIMIT 1";
        try (Connection conexion = ConexionDAO.getConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setString(1, nombreUsuario);
            try (ResultSet resultado = statement.executeQuery()) {
                if (!resultado.next()) {
                    return null;
                }
                Usuario usuario = new Usuario(resultado.getLong("id"),
                        resultado.getString("nombre"), resultado.getString("usuario"),
                        resultado.getString("correo"), resultado.getString("rol"));
                return new RegistroAutenticacion(usuario, resultado.getString("password_hash"));
            }
        }
    }

    /** Crea una cuenta con rol USUARIO por defecto; nunca acepta el rol del navegador. */
    public boolean registrar(String nombre, String usuario, String correo, String hash)
            throws SQLException {
        String sql = "INSERT INTO usuarios (nombre, usuario, correo, password_hash, rol) "
                + "VALUES (?, ?, ?, ?, 'USUARIO')";
        try (Connection conexion = ConexionDAO.getConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setString(1, nombre);
            statement.setString(2, usuario);
            statement.setString(3, correo);
            statement.setString(4, hash);
            return statement.executeUpdate() == 1;
        }
    }
}
