-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS ProyectoG7;
USE ProyectoG7;

-- Tabla Usuario
CREATE TABLE IF NOT EXISTS Usuario (
    PK_Cedula VARCHAR(20) PRIMARY KEY UNIQUE,
    Nombre_Completo VARCHAR(100) NOT NULL,
    Correo VARCHAR(100) NOT NULL UNIQUE,
    Contraseña VARCHAR(255) NOT NULL,
    Imagen_Perfil VARCHAR(1024),
    Fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Tabla Razones Sociales
CREATE TABLE IF NOT EXISTS Razon_Social (
    ID_Razon INT AUTO_INCREMENT PRIMARY KEY,
    Nombre_Razon VARCHAR(100) NOT NULL UNIQUE
);

-- Insertar razones sociales comunes en Costa Rica
INSERT INTO Razon_Social (Nombre_Razon) VALUES
('Sociedad Anónima'),
('Sociedad de Responsabilidad Limitada'),
('Empresa Individual de Responsabilidad Limitada'),
('Asociación'),
('Cooperativa');

-- Tabla Actividades Económicas
CREATE TABLE IF NOT EXISTS Actividad_Economica (
    ID_Actividad INT AUTO_INCREMENT PRIMARY KEY,
    Nombre_Actividad VARCHAR(150) NOT NULL UNIQUE
);

-- Insertar actividades económicas comunes según Hacienda CR
INSERT INTO Actividad_Economica (Nombre_Actividad) VALUES
('Servicios profesionales'),
('Comercio al por menor'),
('Comercio al por mayor'),
('Turismo y hospedaje'),
('Tecnología y desarrollo de software'),
('Restaurantes y sodas'),
('Transporte de mercancías'),
('Servicios de salud'),
('Educación privada'),
('Construcción');


CREATE TABLE IF NOT EXISTS Pyme (
    PK_ID_Pyme INT AUTO_INCREMENT PRIMARY KEY,
    Cedula_juridica_fisica VARCHAR(50) NOT NULL,
    FK_Razon_Social INT NOT NULL,
    Nombre_comercial VARCHAR(100),
    FK_Actividad_Economica INT NOT NULL,
    Direccion_fisica VARCHAR(255),
    Correo_empresarial VARCHAR(100),
    Telefono_empresarial VARCHAR(20),
    Imagen_Pyme VARCHAR(1024),
    Fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (FK_Razon_Social) REFERENCES Razon_Social(ID_Razon)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (FK_Actividad_Economica) REFERENCES Actividad_Economica(ID_Actividad)
        ON DELETE RESTRICT ON UPDATE CASCADE
);

-- Tabla intermedia Usuario_Pyme
CREATE TABLE IF NOT EXISTS Usuario_Pyme (
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

-- Tabla Factura
CREATE TABLE IF NOT EXISTS Factura (
    ID_Factura INT AUTO_INCREMENT PRIMARY KEY,
    FK_Cedula_Cliente VARCHAR(100) NOT NULL,
    Monto DECIMAL(10,2) NOT NULL,
    Fecha DATE NOT NULL,
    Descripcion TEXT,
    Estado_factura BOOLEAN,
    FOREIGN KEY (FK_Cedula_Cliente) REFERENCES Usuario(PK_Cedula)
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- Tabla Asesoria
CREATE TABLE Asesoria(
ID_Asesoria INT AUTO_INCREMENT PRIMARY KEY,
Nombre VARCHAR(75),
Fecha DATE NOT NULL,
TipoConsulta VARCHAR (80) 
);
INSERT INTO Asesoria(ID_Asesoria, Nombre, Fecha, TipoConsulta) 
VALUES (1, 'Fiscal', '2025-07-25', 'Cita'),
(2, 'Contabilidad', '2025-08-20', 'Asesoria'),
(3, 'Contabilidad', '2025-07-20', 'Cita');

CREATE TABLE IF NOT EXISTS Casos(
    ID_Caso INT AUTO_INCREMENT PRIMARY KEY,
    FK_Cedula_Usuario VARCHAR(20) NOT NULL,
    Descripcion TEXT,
    Rating INT CHECK (Rating BETWEEN 1 AND 5),
    Fecha_Creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (FK_Cedula_Usuario) REFERENCES Usuario(PK_Cedula)
        ON DELETE CASCADE ON UPDATE CASCADE
);

