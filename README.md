# Country data backend

Java Spring Boot project

Frontend : https://github.com/OGrainger/country-data-frontend

## Installation

This project needs a PostgreSQL. Authentication and url can be modified in 
```
src/main/resources/application.properties
```

Line 10 (`spring.jpa.hibernate.ddl-auto=create`) needs to be commented out for the first app run, allowing table automatic creation. Please comment the line or change value to `validate` after the first run to unsure no data loss


File `data.txt` should be put in same `resources` folder

## Getting started

App is launched via main class `TechnicalAptitudeTestApplication.java`

Endpoint GET `/import` can be used to start batch job.
Other endpoints are used for the frontend part, when import is finished