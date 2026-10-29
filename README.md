# TecnoCatalog Backend

API REST de **TecnoCatalog**, una aplicación web para la consulta y administración de un catálogo de productos. El backend gestiona la autenticación de usuarios, los roles, las categorías, los productos y los productos favoritos.

## Tecnologías utilizadas

- Java 21
- Spring Boot 4.0.9
- Maven
- Spring Web MVC
- Spring Data JPA
- Spring Security
- Spring Validation
- MySQL 8.0.46
- JWT para la autenticación
- Lombok

## Requisitos del sistema

Antes de ejecutar el proyecto, instala lo siguiente:

- **JDK 21:** entorno de desarrollo de Java.
- **MySQL 8.0 o compatible:** motor de base de datos.
- **IDE:** IntelliJ IDEA, Eclipse o Visual Studio Code con las extensiones necesarias.
- **Git:** para clonar el repositorio, si corresponde.
- **Maven:** el proyecto puede ejecutarse mediante Maven Wrapper si incluye los archivos `mvnw` y `mvnw.cmd`.

Verifica las instalaciones con los siguientes comandos:

```bash
java -version
mysql --version
```

## Configuración de la base de datos

1. Inicia el servicio de MySQL.
2. Crea la base de datos:

```sql
CREATE DATABASE tecnocatalog;
```

3. Crea las tablas necesarias para el funcionamiento de la aplicación.

**Importante:** el proyecto utiliza `spring.jpa.hibernate.ddl-auto=update`, por lo que Hibernate crea automáticamente las tablas.

## Configuración de la conexión

Abre el archivo `src/main/resources/application.properties` y verifica la configuración de la conexión:

```properties
spring.application.name=tecnocatalog-backend

spring.datasource.url=jdbc:mysql://localhost:3306/tecnocatalog
spring.datasource.username=tuUsuario
spring.datasource.password=tuContrasena

spring.jpa.hibernate.ddl-auto=update
```

## Instalación y ejecución

### 1. Obtener el proyecto

Clona el repositorio o abre la carpeta del backend en tu IDE.

### 2. Compilar el proyecto

Desde la raíz del proyecto, ejecuta:

En Windows, si el repositorio incluye Maven Wrapper:

```powershell
.\mvnw.cmd clean package
```

En Linux o macOS:

```bash
./mvnw clean package
```

Si no incluye Maven Wrapper, utiliza Maven instalado:

```bash
mvn clean package
```

### 3. Ejecutar la aplicación

En Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

En Linux o macOS:

```bash
./mvnw spring-boot:run
```

También puedes ejecutar la clase principal desde tu IDE.

Por defecto, la API está disponible en:

`http://localhost:8080`

## Endpoints principales

Los endpoints siguientes corresponden a las funcionalidades principales de la aplicación. Las operaciones protegidas requieren autenticación y los permisos correspondientes.

| Recurso | Endpoint | Funcionalidad |
|---|---|---|
| Autenticación | `POST /api/auth/login` | Iniciar sesión |
| Usuarios | `POST /api/auth/registro`* | Registrar usuario |
| Productos | `/api/productos` | Consultar y administrar productos |
| Categorías | `/api/categorias` | Consultar y administrar categorías |
| Roles | `/api/roles` | Consultar y administrar roles |
| Favoritos | `/api/usuarios/{usuarioId}/favoritos` | Consultar favoritos de un usuario |
| Favoritos | `POST /api/usuarios/{usuarioId}/favoritos/{productoId}` | Agregar un producto a favoritos |
| Favoritos | `DELETE /api/usuarios/{usuarioId}/favoritos/{productoId}` | Eliminar un producto de favoritos |

\* Verifica la ruta exacta del registro en el controlador de autenticación antes de entregar la documentación.

Las operaciones de consulta, creación, actualización y eliminación de productos y categorías utilizan los métodos HTTP correspondientes: `GET`, `POST`, `PUT` y `DELETE`.

## Seguridad

- La autenticación se realiza mediante tokens JWT.
- Las solicitudes protegidas deben incluir el token en la cabecera `Authorization` con el formato `Bearer <token>`.
- Las operaciones administrativas están restringidas a usuarios con el rol correspondiente.
- Las rutas públicas y protegidas se determinan mediante la configuración de Spring Security.

## Consideraciones adicionales

- Verifica que MySQL esté activo y que las credenciales sean correctas.
- Asegúrate de que la base de datos y sus tablas existan antes de iniciar la aplicación.
- Si cambias el puerto o la configuración de conexión, actualiza los valores correspondientes.
- No incluyas credenciales, claves JWT ni otros secretos en el repositorio.
