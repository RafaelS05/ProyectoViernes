CREATE DATABASE IF NOT EXISTS ProyectoG7;
USE ProyectoG7;


CREATE USER 'usuario_proyecto'@'localhost' IDENTIFIED BY 'la_Clave';

GRANT ALL PRIVILEGES ON ProyectoG7.* TO 'usuario_proyecto'@'localhost';

FLUSH PRIVILEGES;
-- Tabla Usuario
ALTER TABLE Usuario MODIFY COLUMN Imagen_Perfil VARCHAR(1024);

CREATE TABLE Usuario (
    PK_Cedula VARCHAR(20) PRIMARY KEY unique,
    Nombre_Completo VARCHAR(100) NOT NULL,
    Correo VARCHAR(100) NOT NULL UNIQUE,
    Contraseña VARCHAR(255) NOT NULL,
    Fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Tabla Pyme
ALTER TABLE Pyme MODIFY COLUMN Imagen_Pyme VARCHAR(1024);
CREATE TABLE Pyme (
    PK_ID_Pyme INT AUTO_INCREMENT PRIMARY KEY,
    Razon_social VARCHAR(100) NOT NULL,
    Nombre_comercial VARCHAR(100),
    Actividad_economica VARCHAR(100),
    Direccion_fisica VARCHAR(255),
    Correo_empresarial VARCHAR(100),
    Telefono_empresarial VARCHAR(20),
    Fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP
);


-- Tabla intermedia Usuario_Pyme
CREATE TABLE Usuario_Pyme (
    ID_Usuario_Pyme INT AUTO_INCREMENT PRIMARY KEY,
    FK_Cedula VARCHAR(20),
    FK_ID_Pyme INT,
    Rol VARCHAR(50) NOT NULL DEFAULT 'Propietario',
    Fecha_asociacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (FK_Cedula) REFERENCES Usuario(PK_Cedula)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (FK_ID_Pyme) REFERENCES Pyme(PK_ID_Pyme)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Factura(
ID_Factura INT AUTO_INCREMENT PRIMARY KEY,
FK_Cedula_Cliente VARCHAR (100) NOT NULL,
Monto DECIMAL (10,2) NOT NULL,
Fecha DATE NOT NULL,
Descripcion TEXT,
Estado_factura BOOLEAN,
FOREIGN KEY (FK_Cedula_Cliente) REFERENCES Usuario(PK_Cedula)
);
