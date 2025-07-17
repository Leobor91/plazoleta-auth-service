# plazoleta-auth-service

# Servicio de Autenticación (AuthService)

Este es un servicio de microservicio dedicado a la gestión de autenticación y autorización de usuarios para el ecosistema de Pragma Plaza de Comidas. Proporciona funcionalidades para el registro de usuarios (inicialmente propietarios) y la gestión de roles.

## 🚀 Tecnologías Utilizadas

* **Lenguaje:** Java 17
* **Framework:** Spring Boot 3.x
* **Base de Datos:** MySQL
* **ORM:** Spring Data JPA / Hibernate
* **Gestión de Dependencias:** Gradle
* **Pruebas Unitarias:** JUnit 5, Mockito
* **Análisis de Cobertura de Código:** JaCoCo
* **Documentación API:** Springdoc-openapi (compatible con Swagger UI)
* **Librerías de Utilidad:** Lombok

## 🧩 Arquitectura

El servicio sigue la **Arquitectura Hexagonal**, separando claramente la lógica de negocio (dominio) de los detalles de infraestructura (persistencia, comunicación externa, exposición API).

* **`domain/`**: Contiene la lógica de negocio central (modelos, puertos de entrada `api`, puertos de salida `spi` ).
* **`application/`**: Capa de aplicación, incluye DTOs (Data Transfer Objects) y mappers para la comunicación entre la infraestructura y el dominio, así como los manejadores de comandos y queries.
* **`infrastructure/`**: Implementaciones de los puertos, tanto de entrada (`input/rest` para controladores REST) como de salida (`output/jpa` para adaptadores de persistencia JPA, `output/adapter` para adaptadores de servicios externos).
* **`config/`**: Clases de configuración de Spring.

## 🛠️ Configuración del Entorno de local

Para poner en marcha el proyecto en tu entorno local, sigue los siguientes pasos:

### Prerrequisitos

* **Java Development Kit (JDK) 17 o superior**
* **Gradle** (generalmente incluido con Spring Boot y tu IDE)
* **Base de Datos PostgreSQL:** Necesitas una instancia de MySQL en ejecución.
* **Cliente SQL:** Opcional, pero recomendado (DBeaver, pgAdmin, MySQL Workbench, etc.) para gestionar la base de datos.

### Clonar el Repositorio

```bash


git clone https://github.com/Leobor91/plazoleta-auth-service.git


cd auth-service
```

### Configuración de la Base de Datos
* **
* Al desplegar el servicio por primera vez se creara la base de datos 
`auth_db`. Asegúrate de que tu usuario de MySQL tenga permisos para crear bases de datos.

* Actualiza el archivo de propiedades de la aplicación `src/main/resources/application.yml` con tus credenciales de MySQL:

```yaml

server:
  port: 8081

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/auth_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
    username: your_username
    password: your_password
    driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    hibernate:
      ddl-auto: update # 'update' para desarrollo, 'none' para producción. 'create-drop' para pruebas.
    show-sql: true
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQLDialect
        format_sql: true
```

Reemplaza `your_mysql_username` y `your_mysql_password` con tus credenciales de MySQL.

### Construir el Proyecto

Para compilar el proyecto y descargar las dependencias, ejecuta:

```bash


./gradlew clean build
```
Esto generará un archivo JAR en el directorio `build/libs`.

### Ejecutar el Servicio

Puedes ejecutar la aplicación desde el JAR generado:

```bash


java -jar build/libs/auth-service-0.0.1-SNAPSHOT.jar
```

O directamente desde Gradle:

```bash


./gradlew bootRun
```
La aplicación se iniciará por defecto en http://localhost:8081. (o el puerto configurado en `application.yml`).


---

#  ⚙️ Configuración y Rutas Clave

### puertos
* **Puerto de la Aplicación:** 8081 (configulable en `application.yml`)

---

## Endpoints de API REST

Todos los endpoints están prefijados con `/api/v1/users`.

---

### Registro de Propietario

`POST /api/v1/users/create-owner`

Crea un nuevo usuario con rol de **PROPIETARIO**.

**Cuerpo de la Solicitud (JSON):**

```json
{
  "nombre": "Samuel",
  "apellido": "Ramirez",
  "correo": "samuel.Ramirez@example.com",
  "celular": "+5730987543",
  "documento_de_identidad": "1002374567",
  "fecha_de_nacimiento": "1992-11-20",
  "contraseña": "SecurePass!789"
}
```
---

### Respuestas Comunes para Registro:

* **`201 Created`**: El usuario fue creado exitosamente.
* **`400 Bad Request`**: Los datos enviados no son válidos (ej. formato incorrecto, campos obligatorios faltantes).
* **`404 Not Found`**: no se encontro algun recurso (usuario, rol etec).
* **`409 Conflict`**: Se presentó un conflicto de datos; esto puede ocurrir si el correo electrónico, número de teléfono o documento de identidad ya están registrados, o si el rol especificado no existe.
* **`403 Forbidden`**: No tienes los permisos necesarios para realizar esta acción, posiblemente debido a restricciones de seguridad que impiden la creación del rol solicitado.
* **`500 Internal Server Error`**: Ocurrió un error inesperado en el servidor durante el procesamiento de la solicitud.

---

## Verificar Propietario por ID

Este endpoint permite consultar si un usuario específico existe y, en caso afirmativo, si posee el rol de **PROPIETARIO**.

---

### `GET /api/v1/users/isOwner`

**Descripción:** Verifica la existencia y el rol de un usuario.

**Parámetros de Consulta:**

* `userId` (tipo: `Long`, **obligatorio**): El ID del usuario que se desea verificar.

**Respuestas Comunes:**

* **`202 Accepted`**:
  * **Cuerpo de la respuesta:** 
   ```json
    {
      "id": 5,
      "nombre": "Sofia",
      "apellido": "Ramirez",
      "documento_de_identidad": "1002345678",
      "celular": "+573009876543",
      "correo": "sofia.ramirez@example.com",
      "fecha_de_nacimiento": "1992-11-20",
      "rol": "PROPIETARIO"
    }
  
   ```
  * Se devuelve si el usuario con el `userId` especificado existe y tiene el rol de **PROPIETARIO**.
* **`404 Not Found`**:
  * Se devuelve si no se encuentra ningún usuario con el `userId` especificado.
---

### Gestión de Excepciones

Las excepciones personalizadas (```PersonalizedException```, ```PersonalizedBadRequestException```, ```PersonalizedNotFoundException```) lanzadas por la lógica de negocio son capturadas por un 
```@ControllerAdvice``` y mapeadas a respuestas HTTP ```409 Conflict```, ```400 Bad Request```, ```404 Not Foundt```
, con un cuerpo JSON que contiene el mensaje de error.

* Ubicación del ControllerAdvice: 
```com.pragma.plazadecomidas.authservice.domain.exception.GlobalExceptionHandler.java```

---

## 📚 Documentación API (Swagger UI)

Una vez que el servicio esté corriendo, puedes acceder a la documentación interactiva de la API a través de Swagger UI en:

* [http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html)

Aquí encontrarás todos los endpoints disponibles, sus parámetros de solicitud, ejemplos de respuesta y códigos de estado.

---

## 🧪 Pruebas y Cobertura de Código

### Ejecutar Tests y Generar Reporte de Cobertura

Para ejecutar todas las pruebas unitarias y generar un reporte de cobertura de código con JaCoCo, utiliza el siguiente comando:

```bash

# ./gradlew clean test jacocoTestReport
 
```



---

### Acceder al Reporte de Cobertura

Una vez generadas las pruebas, puedes ver el **reporte HTML de JaCoCo** en tu navegador:

**Ruta Local:** `build/reports/jacoco/html/index.html`

Este reporte te mostrará el porcentaje de líneas, ramas e instrucciones de tu código que están cubiertas por los tests.

---

```gradle
tasks.jacocoTestReport {
	dependsOn test
	reports {
		xml.required = true
		csv.required = false
		html.required = true
	}

	afterEvaluate {
		classDirectories.setFrom(files(classDirectories.files.collect {
			fileTree(dir: it,
					exclude: [
							'com/pragma/plazadecomidas/authservice/AuthServiceApplication.class',
							'**/infrastructure/configuration/**',
							'**/application/dto/**',
							'**/infrastructure/exception/**',
							'**/domain/model/**',
							'**/infrastructure/out/jpa/entity/**'
						])
		}))
	}
}
```

---
