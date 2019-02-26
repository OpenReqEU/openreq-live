# OpenReq!Live - Requirements Engineering Platform [![EPL 2.0](https://img.shields.io/badge/License-EPL%202.0-blue.svg)](https://www.eclipse.org/legal/epl-2.0/)

This service was created as a result of the OpenReq project funded by the European Union Horizon 2020 Research and Innovation programme under grant agreement No 732463.
This project is based on Java Spring 1.5.

## Technical description
### What does the service do

OpenReq!Live is an online platform that assists stakeholders in their everyday requirement engineering related tasks concerning software projects.
Furthermore, it supports stakeholders in the release planning process and allows them to share the results with other people.

### Which technologies are used
This service requires Java 1.8+

- Hibernate (-> https://www.docker.com/)
- Lombok (-> https://projectlombok.org/)
- MySQL (-> https://www.mysql.com/)
- Thymleaf Spring (-> https://github.com/thymeleaf/thymeleaf-spring)
- Guava (-> https://github.com/google/guava)
- Springfox Swagger (-> https://github.com/springfox/springfox)
- jsoup: Java HTML Parser (-> https://github.com/jhy/jsoup)
- Jayway JsonPath (-> https://github.com/json-path/JsonPath)
- Java Simplified Encryption (https://github.com/jboss-fuse/jasypt/tree/master/jasypt)

### How to install it
To run the server and to install all dependencies, please execute the following commands from the project root directory:

```
mvn clean install
java -jar target/openreq-1.0.jar
```

## How to use it (high-level description)

Once the server is running, open your browser and call the following URL to see the API documentation:

```
http://localhost:9001/swagger-ui.html
```

### Notes for developers
None.

### Sources
None.

### How to contribute
See OpenReq project contribution [Guidlines](https://github.com/OpenReqEU/OpenReq/blob/master/CONTRIBUTING.md "Guidlines")

## License
Free use of this software is granted under the terms of the EPL version 2 (EPL2.0).
