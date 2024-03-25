# API REST para Administración de Clientes y Productos Financieros

Esta API REST ha sido desarrollada como parte de una prueba técnica para la empresa QUIND. La aplicación permite la administración de clientes y productos financieros para una entidad financiera.

## Configuración de la Base de Datos

Para el correcto funcionamiento de la aplicación, se debe crear una base de datos con el nombre `entidad_financiera` en un servidor MySQL. Se puede utilizar el siguiente archivo de configuración para Spring Boot (`application.properties`) como referencia para la configuración de la base de datos:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/entidad_financiera
spring.datasource.username=samuel
spring.datasource.password=12345
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=create

# Puerto del servidor Tomcat
server.port=8080
