# Backend Microservices System API Template (Java)
Last Update: 30 October 2023

## Introduction 
This is the sample project template for developing Microservice System API. Please read carefully the following guidelines before starting your development.

The general rule of thumb:
1. Please strictly follow the framework.
2. Violation of the framework is STRICTLY PROHIBITED. Please discuss with Architecture team and get the team's endorsement if any changes are required.
3. **DO NOT PUT ANY BUSINESS LOGIC IN SYSTEM API.**
4. **DO NOT MAKE ANY CODE TO DIRECTLY MANIPULATE OTHER DOMAINS**. 
5. **3rd party API calls are STRICTLY PROHIBITED**.
6. Developers should take all the responsibilities if any incidents occurred due to framework violations.

## Technologies
The following technologies / tools are adopted.
1. Java JDK: [OpenJDK 17](https://openjdk.org/projects/jdk/17/)
2. IoC and MVC Framework: [Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/html/) 
3. REST API (CRUD) Framework: [Spring Data REST](https://docs.spring.io/spring-data/rest/reference/index.html)
5. DB Version Control: [Liquibase]()

## Development Guidelines

### Namespace Definition
Please strictly follow the namespace structure. Please DO NOT create other directories.
1. Directory: `src/main/<package name>`

   | Name         |      Purpose                     | 
   |--------------|----------------------------------|
   | `<root>`     | Application main class only      |
   | `dto`        | Data To Object, including Record |
   | `entity`     | Hibernate Entity                 |
   | `exception`  | Exception class                  |
   | `repository` | Database repository              |

2. Directory: `src/main/resources`

   | Name           |      Purpose              |
   |----------------|---------------------------|
   | `<root>`       | Configuration files       |
   | `db/changelog` | DB change using Liquibase |
  
### Spring Data REST implementation 
It is used for creating CRUD operation of the entity. Simply follow the steps below to automatically create CRUD APIs.
1. Create Database Entity (Hibernate), e.g.
   ```
   @Entity
   @Data
   @Table(name="language")
   public class Language {
    
       @Id
       @Column(name="language_code")
       private String languageCode;

       @Column(name="name")
       private String name;

       @Column(name="created_date")
       @Temporal(TemporalType.TIMESTAMP)
       private Date createdDate;

       @Column(name="created_by")
       private String createdBy;

       @Column(name="updated_date")
       @Temporal(TemporalType.TIMESTAMP)
       private Date updatedDate;

       @Column(name="updated_by")
       private String updatedBy;
   }
   ```
   Note: Please use Lombok to avoid any get / set code.

2. Create Repository that extends `CrudRepository`
   ```
   public interface LanguageRepository extends CrudRepository<Language, Integer> {
    
   }
   ```
   or extend `PagingAndSortingRepository` if you would like to use paging and record limit
   ```
   public interface LanguageRepository extends 
        PagingAndSortingRepository<Language, Integer>, 
        CrudRepository<Language, Integer> {
    
   }
   ```
The basic CRUD has been included and the search method has also been included in Spring Data REST framework. To keep the cleaniness and the quality of the framework, please do not add your own method in repository to serve the specific purpose. If it's really required, please contact Architecture Team and get the team's endorsement first.

## 3rd party API call
For system API to communicate with 3rd party system to get or save the data, you may do the call using SOAP (WebServiceClient) or HTTP (using Feign Client). The following consideration must be taken into account.
1. Circuit breaker: In case of any service failure, it will not keep calling the service until it resumes to normal.
2. Rollback in case of any error: It depends on the actual business logic. If it is required, we need to have a rollback mechanism (e.g. use RollabckFactory in Feign client to run the compensation)

### HTTP Method and Response Code

It is very important to return the correct response code. We should follow the industry standard in order to make the API meaningful and reusable.

Please strictly follow the HTTP method rules.
| Action                   | HTTP Method | Success Response Code                                   |
|--------------------------|-------------|---------------------------------------------------------|
| Read Data                | `GET`       | 200 (full record), 204 (No Data), 206 (Partial Content) |
| Create                   | `POST`      | 201                                                     |
| Full Update (All fields) | `PUT`       | 200                                                     |
| Partial Update           | `PATCH`     | 200                                                     |
| Delete                   | `DELETE`    | 200                                                     |

Response Code for specific scenario (Please strictly follow)
| Scenario                                               | HTTP Response Code |
|--------------------------------------------------------|--------------------|
| Client-side failure (e.g. Missing or wrong parameters) | 400                |
| Server-side failure (Exception only)                   | 500                | 

Note:
**DO NOT USE response code 500** for any client side failure (i.e. validation failed).


### Validation
Validation should be put in Entity class. You may use validation annotation (e.g. `@NotNull`, `@Email`), or write your own validator for handling complex validation.
Please refer to the [validation](https://docs.spring.io/spring-data/rest/reference/validation.html) for reference.

### i18n handling
i18n is defined in application.properties
```
# i18n properties
# Whether to always apply the MessageFormat rules, parsing even messages without arguments.
spring.messages.always-use-message-format=false

# Comma-separated list of basenames
spring.messages.basename=messages

# Loaded resource bundle files cache duration.
# When not set, bundles are cached forever.
# If a duration suffix is not specified, seconds will be used.
spring.messages.cache-duration=

# Message bundles encoding.
spring.messages.encoding=UTF-8

# Whether to fall back to the system Locale
# if no files for a specific Locale have been found.
spring.messages.fallback-to-system-locale=true

# Whether to use the message code as the default message instead of throwing a "NoSuchMessageException".
# Recommended during development only.
spring.messages.use-code-as-default-message=true
```

From the above example, the i18n values are stored in `messages_[local].properties`. You may add other filename prefix in 
`spring.messages.basename`.

In the project template i18n is applied in validation message.

### Exception Handling
Basically there is no need to handle exception explicitly in system API using Spring Data REST. The only thing we need to handle is to convert `ConstraintViolationException` from response code 500 (Internal Server Error) to 400 (Bad Request). It is achieved by `ExceptionHandler` below.
```
 @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<APIErrorResponse> handleConstraintViolationException(ConstraintViolationException exception, Locale locale) {
        APIErrorResponse errorResponse = new APIErrorResponse();
        errorResponse.setTimestamp(Calendar.getInstance().getTime());
        errorResponse.setStatus(HttpStatus.BAD_REQUEST);
        errorResponse.setMessage(exception.getMessage());
        for (ConstraintViolation<?> violation: exception.getConstraintViolations()) {
            errorResponse.getConstraintErrors().add(messageSource.getMessage(violation.getMessage(), null, locale));
        }
        return ResponseEntity.badRequest().body(errorResponse);
    }
```

### Liquibase Changelog
Database schema should be maintained by API. Please use Liquibase for database schema update instead of running the scripts manually.
Procedure of adding a new version:
1. Create a new sql file in src/resources/db/changelog
2. Add an entry in db-changelog.json
```
      "changeSet": {
        "id": "<version>-<seq>",
        "author": "Anthony Tam",
        "changes": [
          {
            "sqlFile":{
              "dbms":"!h2, postgresql",
              "splitStatements":true,
              "path":<SQL filename>,
              "stripComments":true
            }
          }
        ]
      }
```
Note: `<version>` should follow Maven version, and `<seq>` is the number of execution.

### Time Zone Handling
Developes should be aware of timezone when handling the date / time value in their logic. Here's rule of thumb.
1. It is a common practice that all the time should be stored in database in **UTC timezone**. Developers should make sure that the timezone must be provided when saving the time value to database.
2. API should be flexible to allow the time display in different timezone by configuration or client profile.

It is possible to change the display timezone by the following ways.
1. Define time zone using environment variable when startup the API. e.g.
```
java -Duser.timezone="Asia/Hongkong" poc.msa.system.pocsvc.PocSvcApplication
```

2. Define time zone in client's profile and convert to client's time zone when displaying time. It should be done in program level.

## Platform specific configuration

### Authentication
TBC

### Azure
TBC

## Unit Test
TBC

## Swagger
OpenAPI document is generated automatically after starting the application. It can be viewed from the URL http://localhost:8081/api-doc. 
Web version can also be viewed from the URL http://localhost:8081/swagger-ui/index.html


## Version Control
Sematic versioning approach is adopted and the version number should be marked in `pom.xml`. 
1. Syntax: `<Major Version>.<Minor version>.<Incremental version>`
2. Major Version: For release that is backward incompatible
3. Minor Version: For release that is backward compatible
4. Incremental Version: Bug Fix

## Copyright
This is the property of HKBN Enterprise Solutions and it is solely used for HKBN internal / external projects. Distribution of the project template to unauthorized person is STRICTLY PROHIBITED.