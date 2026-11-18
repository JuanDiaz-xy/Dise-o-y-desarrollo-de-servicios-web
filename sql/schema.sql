-- Ejecutar en SQL Editor de TiDB Cloud o en MySQL Workbench conectado a TiDB.
-- Seleccionar la base de datos creada en TiDB antes de ejecutar el script.
CREATE TABLE IF NOT EXISTS usuarios (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    usuario VARCHAR(30) NOT NULL UNIQUE,
    correo VARCHAR(150) NOT NULL UNIQUE,
    password_hash VARCHAR(100) NOT NULL,
    rol VARCHAR(20) NOT NULL DEFAULT 'USUARIO',
    creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Para comprobar el registro sin exponer el hash ni las contraseñas:
-- SELECT id, nombre, usuario, correo, rol, creado_en FROM usuarios;
