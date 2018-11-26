# RVN Maven Project
 
 Usage:

* Adjust your database connection parameters in file:
 
```
    src/main/resources/database.properties
```

* Execute database migrations: 

```
    mvn db-migrator:create
    mvn db-migrator:migrate
```

* Build program: 

```
    mvn clean install
```    

## Instrumentation

If you run  this project from an IDE, you will need to instrument models before running. Here is how: 

    mvn process-classes
 
 another way of doing the same is: 
 
    mvn activejdbc-instrumentation:instrument
