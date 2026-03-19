# ProyectoViernes

Aplicación web construida con **Spring Boot + Thymeleaf + MySQL** para apoyar a personas usuarias y PyMEs con registro de perfiles, administración de empresa, facturación, asesorías, casos de soporte, recomendaciones y una sección de asistencia con IA.

## ¿Qué hace el proyecto?

La aplicación reúne varias funcionalidades orientadas al acompañamiento de PyMEs:

- **Registro e inicio de sesión de usuarios**.
- **Gestión de perfil** con imagen.
- **Registro y edición de PyME** asociada al usuario.
- **Facturación básica** con numeración automática.
- **Agenda de asesorías**.
- **Registro y seguimiento de casos**.
- **Recomendaciones** según la actividad económica de la PyME.
- **Secciones informativas**: ayuda, preguntas frecuentes, obligaciones y formalización.
- **Búsqueda interna** para redirigir a módulos relevantes.
- **Asistente con Google Gemini** para generar respuestas desde la interfaz.

## Tecnologías principales

- **Java 17**
- **Spring Boot 3.4.5**
- **Spring MVC**
- **Spring Data JPA**
- **Thymeleaf**
- **MySQL**
- **Bootstrap 5** vía WebJars
- **Firebase Admin SDK** para carga de imágenes
- **Google Gemini API**
- **Maven**
- **Docker**

## Estructura del proyecto

```text
src/main/java/com/proyecto
├── controller/    # Controladores web por módulo
├── domain/        # Entidades del dominio
├── repository/    # Repositorios JPA
└── service/       # Lógica de negocio e integraciones

src/main/resources
├── templates/     # Vistas Thymeleaf
├── static/        # CSS, JS, imágenes y PDF
├── firebase/      # Credenciales usadas por Firebase
└── application.properties
```

## Módulos disponibles

| Módulo | Ruta principal | Descripción |
|---|---|---|
| Inicio | `/` | Landing principal con secciones informativas. |
| Usuarios | `/usuario/registro`, `/login` | Registro, autenticación y perfil. |
| PyME | `/pyme/mi-pyme` | Registro o edición de la empresa del usuario. |
| Facturación | `/factura/` | Creación y visualización de facturas. |
| Asesorías | `/asesoria/asesoria` | Agenda de asesorías y chat simulado. |
| Casos | `/casos/lista` | Registro, edición y seguimiento de casos. |
| Recomendaciones | `/recomendaciones` | Recomendaciones por actividad económica. |
| IA | `/ai` | Interfaz para generar texto con Gemini. |
| Obligaciones | `/obligaciones` | Información general para PyMEs. |
| Formalización | `/formalizacion` | Guía de formalización protegida por sesión. |
| Cursos | `/cursos` | Material y recursos de aprendizaje. |
| Ayuda y soporte | `/ayuda`, `/soporte` | Información de apoyo al usuario. |

## Requisitos previos

Antes de ejecutar el proyecto, asegúrate de tener instalado:

- **JDK 17**
- **Maven 3.8+**
- **MySQL 8+**
- Acceso a una cuenta/proyecto de **Firebase** si vas a usar carga de imágenes
- Una API key válida de **Google Gemini** si vas a usar el módulo de IA

## Configuración local

### 1. Clonar el repositorio

```bash
git clone <url-del-repositorio>
cd ProyectoViernes
```

### 2. Crear la base de datos

El repositorio incluye un script SQL inicial:

```bash
mysql -u root -p < ProyectoG7_SQL.sql
```

Ese script crea la base `ProyectoG7` y carga tablas base como `Usuario`, `Pyme`, `Factura`, `Asesoria` y `Casos`.

### 3. Configurar `application.properties`

El archivo `src/main/resources/application.properties` ya incluye propiedades para:

- nombre de la app
- puerto del servidor
- conexión MySQL
- JPA / Hibernate
- mensajes internacionales
- integración con Google Gemini

Valores a revisar antes de ejecutar:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ProyectoG7
spring.datasource.username=usuario_proyecto
spring.datasource.password=la_Clave
server.port=80
```

> **Importante:** para un entorno real, conviene mover credenciales, claves API y certificados fuera del repositorio y gestionarlos con variables de entorno o secretos del entorno de despliegue.

### 4. Configurar Firebase

El proyecto espera un archivo de credenciales en:

```text
src/main/resources/firebase/
```

Si usas otro archivo o proyecto de Firebase, reemplázalo por tus credenciales válidas.

### 5. Configurar Gemini

Si deseas habilitar el módulo de IA, define una API key válida en la configuración:

```properties
google.gemini.apiKey=TU_API_KEY
google.gemini.model=gemini-2.0-flash
google.gemini.baseUrl=https://generativelanguage.googleapis.com
```

## Cómo ejecutar el proyecto

### Opción 1: Maven

```bash
mvn spring-boot:run
```

### Opción 2: Empaquetar el `.jar`

```bash
mvn clean package
java -jar target/Proyecto_G7-1.jar
```

> El nombre final del `.jar` puede variar según la configuración efectiva del build.

Luego abre en el navegador:

```text
http://localhost:80
```

Si cambias el puerto en `application.properties`, usa ese nuevo puerto.

## Cómo ejecutar con Docker

El repositorio incluye un `Dockerfile`.

### Construcción

```bash
docker build -t proyecto-viernes .
```

### Ejecución

```bash
docker run -p 80:80 proyecto-viernes
```

> Asegúrate de que la aplicación pueda acceder a MySQL, Firebase y las credenciales necesarias desde el contenedor.

## Flujo básico de uso

1. Registrar un usuario.
2. Iniciar sesión.
3. Crear o editar la PyME en `Mi PyME`.
4. Consultar recomendaciones asociadas a la actividad económica.
5. Registrar facturas, asesorías o casos según necesidad.
6. Usar el módulo `/ai` para generar apoyo textual con Gemini.

## Notas importantes

- La aplicación depende de una **base de datos MySQL activa**.
- El proyecto usa **sesión HTTP** para manejar el usuario autenticado.
- Algunas vistas requieren inicio de sesión previo.
- El módulo de IA depende de conectividad externa y una clave válida.
- La carga de imágenes depende de la configuración correcta de Firebase.

## Mejoras recomendadas

Si se continúa el desarrollo, estas serían mejoras útiles:

- externalizar credenciales y secretos
- agregar pruebas unitarias e integración
- incorporar Spring Security formal en lugar de login manual
- añadir validaciones y manejo de errores más consistente
- documentar endpoints y flujos con mayor detalle
- corregir y validar el artefacto generado por Docker/Maven para asegurar que el nombre del `.jar` coincida con el `Dockerfile`

## Comandos útiles

```bash
mvn clean package
mvn spring-boot:run
git status
```

## Archivos relevantes

- `pom.xml`: dependencias y configuración Maven.
- `src/main/resources/application.properties`: configuración principal.
- `ProyectoG7_SQL.sql`: base de datos inicial.
- `Dockerfile`: construcción de imagen Docker.
- `src/main/java/com/proyecto/controller/`: módulos web principales.

## Estado de la documentación

Este README fue redactado para servir como guía inicial de instalación, ejecución y comprensión funcional del proyecto. Si el sistema evoluciona, conviene actualizarlo junto con cambios de configuración, módulos y despliegue.
