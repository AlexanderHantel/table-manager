# Table manager
It is a REST API for creating, editing, removing 
tables and their content.

## Services
Operations available for tables:
- Create a new table with any number of columns
- Delete table by name
- Rename table
- Add a new column to existing table
- Rename column
- Change column data type
- Delete column  

Operations available for tables content:
- Insert new entity
- Update entity
- Find entity by ID
- Get all entities from specified table
- Delete entity

## Frameworks used
- Maven
- Spring Boot
- Spring JDBCTemplate
- PostgreSQL
- Liquibase
- Spring Test + JUnit5
- SpringDoc + Swagger

## Database
This application works with PostgreSQL. It needs two 
databases for runtime and tests. So, you should prepare 
your local databases before the build and run:
- Create "tablemanager" and "tablemanager-test" databases
- Path to property file for runtime database: **src\main\resources\application.properties**
- Path to property file for test database: **src\test\resources\application-test.properties**
- In these property files you can specify tablenames, username and password for DB you're just created.

## Build and Run
The app is built and run with Maven command: **$ mvn test spring-boot:run**. Press **Ctrl+C** in command line to stop the app.

## Tests
Integration tests will run automatically by Maven run command, or by command **$ mvn test**, or you can run
 it manually in IDE.

## Documentation
When the app is running you can see description of REST methods in Swagger UI:
http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/  
On the first run Liquibase will create default table "test" in runtime DB,
so you can use all operations with Swagger button **Try it now!**.
