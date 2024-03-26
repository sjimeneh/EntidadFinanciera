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
```

## API Entidad Financiera

**Descripción:** Esta API proporciona acceso a la información de clientes, productos (cuentas) y transacciones de una entidad bancaria.

**Base URL:** http://localhost:8080/api/v1

## Endpoints (Puntos de acceso)

### Clientes (Customers)

**GET: Consultar Cliente por ID (Consulta un cliente por su identificador)**

```
http://localhost:8080/api/v1/customer/find/2
```

**GET: Consultar Todos los Clientes (Obtiene una lista de todos los clientes)**

```
http://localhost:8080/api/v1/customer/find/all
```

**POST: Crear un Cliente (Crea un nuevo cliente)**

```json
{
  "identificationType": "CC",
  "identificationNumber": "1039101630",
  "firstName": "Samuel",
  "lastName": "Jimenez",
  "email": "samuel.hernandez@example",
  "dateOfBirth": "1998-01-31"
}
```

```
http://localhost:8080/api/v1/customer/create
```

**PUT: Actualizar Cliente por ID (Actualiza la información de un cliente)**

```
http://localhost:8080/api/v1/customer/update/2
```

```json
{
  "identificationType": "CC",
  "identificationNumber": "1039101630",
  "firstName": "Samuel",
  "lastName": "Jimenez",
  "email": "samuel.jimenez@example",
  "dateOfBirth": "1998-01-31"
}
```

**DELETE: Eliminar Cliente por ID (Elimina un cliente)**

```
http://localhost:8080/api/v1/customer/delete/1
```

### Productos (Cuentas) - (Products)

**GET: Consultar Producto por ID (Obtiene un producto por su identificador)**

```
http://localhost:8080/api/v1/product/find/1
```

**GET: Consultar Todos los Productos (Obtiene una lista de todos los productos)**

```
http://localhost:8080/api/v1/product/find/all
```

**GET: Consultar Productos por Numero de Cuenta (Obtiene un producto por su número de cuenta)**

```
http://localhost:8080/api/v1/product/find/number/5345522270
```

**POST: Crear un Producto (Crea un nuevo producto)**

```json
{
"productType": "CURRENT_ACCOUNT",
"productNumber": "",
"status": "ACTIVE",
"balance": 2000.00,
"exemptFromGmf": false,
"customer": {
  "id": 2
  }
}
```

```
http://localhost:8080/api/v1/product/create
```

**PUT: Actualizar el estado de un producto (Actualiza el estado de un producto - ACTIVE - INACTIVE - CANCELED)**

```
http://localhost:8080/api/v1/product/update/status?id=2&status=INACTIVE
```

**PUT: Actualizar el monto de un producto (Actualiza el saldo de un producto)**

```
http://localhost:8080/api/v1/product/update/balance?id=1&balance=100
```

**DELETE: Eliminar Producto por ID (Elimina un producto)**

```
http://localhost:8080/api/v1/product/delete/1
```

### Transacciones (Transactions)

**GET: Consultar Transacción por ID (Obtiene una transacción por su identificador)**

```
http://localhost:8080/api/v1/transaction/find/1
```

**GET: Consultar Todas las Transacciones (Obtiene una lista de todas las transacciones)**

```
http://localhost:8080/api/v1/transaction/find/all
```

**POST: Crear una Transacción (Crea una nueva transacción)**

#### Transferencia

```json
{
"transactionType": "TRANSFER",
"amount": 1000,
"originProduct": {
"id": 1
},
"destinationProduct": {
"id": 2
}
}
```

#### Consignación

```json
{
  "transactionType": "DEPOSIT",
  "amount": 1000,
  "destinationProduct": {
    "id": 2
  }
}
```

#### Retiro

```json
{
  "transactionType": "WITHDRAWAL",
  "amount": 1000,
  "originProduct": {
    "id": 2
  }
}
```