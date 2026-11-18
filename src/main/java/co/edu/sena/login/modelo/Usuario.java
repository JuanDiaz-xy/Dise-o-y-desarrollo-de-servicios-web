package co.edu.sena.login.modelo;

/** Modelo del usuario autenticado. Nunca incluye la contraseña ni su hash. */
public final class Usuario {
    private final long id;
    private final String nombre;
    private final String usuario;
    private final String correo;
    private final String rol;

    public Usuario(long id, String nombre, String usuario, String correo, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.usuario = usuario;
        this.correo = correo;
        this.rol = rol;
    }

    public long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getUsuario() { return usuario; }
    public String getCorreo() { return correo; }
    public String getRol() { return rol; }
}
