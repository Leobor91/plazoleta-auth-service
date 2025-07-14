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

## 🛠️ Configuración del Entorno de Desarrollo

Para poner en marcha el proyecto en tu entorno local, sigue los siguientes pasos:

### Prerrequisitos

* **Java Development Kit (JDK) 17 o superior:** Asegúrate de tenerlo instalado y configurado en tu `PATH`.
* **Gradle:** Preferiblemente usar el wrapper de Gradle incluido en el proyecto (`./gradlew`).
* **Base de Datos PostgreSQL:** Necesitas una instancia de MySQL en ejecución.
* **Cliente SQL:** Opcional, pero recomendado (DBeaver, pgAdmin, MySQL Workbench, etc.) para gestionar la base de datos.

### Clonar el Repositorio

```bash


git clone <URL_DE_TU_REPOSITORIO>
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

Asegúrate de reemplazar your_username y your_password con tus credenciales de MySQL.

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
La aplicación se iniciará por defecto en http://localhost:8081.


---

#  ⚙️ Configuración y Rutas Clave

### puertos
* **Puerto de la Aplicación:** 8081 (configulable en `application.yml`)

## Endpoints de API REST

Todos los endpoints están prefijados con `/api/v1/users.`

* Registro de Propietario:
  - POST` /api/v1/users/owner`
    - Crea un nuevo usuario con rol de PROPIETARIO.
    -  Cuerpo de la Solicitud (JSON):
```json
{
    "name": "Juan",
    "lastName": "Pérez",
    "identityDocument": "1234567890",
    "phoneNumber": "+573101234567",
    "email": "juan.perez@example.com",
    "password": "StrongPassword123",
    "birthDate": "1990-05-15"
}
```

* Respuestas Comunes:

  - ```201 Created```: Usuario creado exitosamente.

  - ```400 Bad Request```: Datos de entrada inválidos (ej. formato, campos obligatorios).
  
  -  ```409 Conflict```: Conflictos de datos (email, teléfono, documento ya existen) o rol no encontrado.
  
  -  ```403 Forbidden```: Si hay restricciones de seguridad y el usuario no tiene permisos.
  
  -  ```500 Internal Server Error```: Errores inesperados del servidor.

* Otros Endpoints:


### Gestión de Excepciones

Las excepciones personalizadas (```PersonalizedException```) lanzadas por la lógica de negocio son capturadas por un 
```@ControllerAdvice``` y mapeadas a respuestas HTTP ```409 Conflict```
, con un cuerpo JSON que contiene el mensaje de error.

* Ubicación del ControllerAdvice: 
```com.pragma.plazadecomidas.authservice.domain.exception.GlobalExceptionHandler.java```

---

## 📄 Documentación API (Swagger UI)

Una vez que la aplicación esté en ejecución, puedes acceder a la **documentación interactiva de la API** a través de Swagger UI:

**URL:** `http://localhost:8081/swagger-ui/index.html`

Aquí podrás ver todos los **endpoints disponibles**, sus **parámetros de solicitud**, **modelos de datos** y **ejemplos de respuestas**, incluyendo los **códigos de error 409 personalizados**.

---

## 🧪 Pruebas y Cobertura de Código

### Ejecutar Tests y Generar Reporte de Cobertura

Para ejecutar todas las pruebas unitarias y generar un reporte de cobertura de código con JaCoCo, utiliza el siguiente comando:

```bash

# ./gradlew clean test jacocoTestReport
 
```

Markdown

---

### Acceder al Reporte de Cobertura

Una vez generadas las pruebas, puedes ver el **reporte HTML de JaCoCo** en tu navegador:

**Ruta Local:** `build/reports/jacoco/html/index.html`

Este reporte te mostrará el porcentaje de líneas, ramas e instrucciones de tu código que están cubiertas por los tests.

---

### Umbrales de Cobertura (Opcional)

Si has configurado umbrales de cobertura en tu `build.gradle`, el build fallará si la cobertura cae por debajo de los porcentajes definidos.

```gradle
// Ejemplo de configuración en build.gradle para umbrales y exclusiones
tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            element = 'BUNDLE'
            limit {
                counter = 'LINE'
                value = 'COVEREDRATIO'
                minimum = 0.80 // Mínimo 80% de cobertura de líneas
            }
            limit {
                counter = 'BRANCH'
                value = 'COVEREDRATIO'
                minimum = 0.60 // Mínimo 60% de cobertura de ramas
            }
            // Excluir paquetes de datos/POJOs que no contienen lógica de negocio compleja
            excludes = [
                    'com.pragma.plazadecomidas.authservice.domain.model.*',
                    'com.pragma.plazadecomidas.authservice.infrastructure.output.jpa.entity.*',
                    'com.pragma.plazadecomidas.authservice.infrastructure.exception.*', // Si tus excepciones no tienen lógica compleja
                    'com.pragma.plazadecomidas.authservice.infrastructure.config.*',
                    'com.pragma.plazadecomidas.authservice.AuthServiceApplication'
            ]
        }
    }
}
```

---

## 📂 Estructura del Proyecto (Capas Limpias / Hexagonal)

El proyecto sigue una **arquitectura de capas limpias (Clean Architecture)**, inspirada en la arquitectura hexagonal, para separar las preocupaciones y facilitar la mantenibilidad y testabilidad.

* **`domain`**: Contiene la **lógica de negocio central**, modelos (POJOs), interfaces de puertos (SPIs) y excepciones de negocio.
    * `model`: Clases de dominio que representan **entidades de negocio**.
    * `api`: Implementaciones de los **puertos de entrada (driving adapters)**. Ej: `UserServicePortImpl`.
    * `spi`: Interfaces de los **puertos de salida (driven adapters)**. Ej: `IUserPersistencePort`.
    * `exception`: **Excepciones personalizadas** de negocio.
    * `util`: Clases de utilidad con lógica de **validación genérica**.

* **`application`**: Contiene **mappers** y **handlers de comandos/queries**.
    * `mapper`: Interfaces y clases para **mapear entre modelos de dominio y DTOs/Entidades**.
    * `handler`: Clases que **orquestan el uso de los servicios de dominio**.

* **`infrastructure`**: Implementaciones específicas de la tecnología (**bases de datos, REST, seguridad**).
    * `input.rest`: **Controladores REST** (puertos de entrada).
    * `output.jpa.entity`: **Entidades JPA** para la persistencia.
    * `output.jpa.mapper`: Mappers entre **entidades JPA y modelos de dominio**.
    * `output.adapter`: Implementaciones de los **puertos de persistencia (driven adapters)**.
    * `config`: Clases de **configuración** (ej. seguridad, beans).
    * `exception`: Clases de excepción relacionadas con la infraestructura (si las hay).
    * `input.rest.advice`: **Manejo global de excepciones** para la capa REST.
