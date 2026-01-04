![restassured_logo](assets/restassured_logo.png)

# Overview
A comprehensive, data-driven test automation framework for Swagger PetStore API using RestAssured with built-in 
reporting and logging capabilities.

## Features
 - Robust API testing with built-in validation.
 - Modular design with separation of concerns.
 - Data-Driven Testing.
 - Interactive reports and comprehensive logging.
 - JSON schema validation.

## Technologies Used
|                   |                       |
|-------------------|-----------------------|
| Language          | **Java**              |
| Build Tool        | **Maven**             |
| UI Framework      | **Playwright**        |
| Testing Framework | **TestNG**            |
| Design Pattern    | **Page Object Model** |
| Logging           | **Log4j2**            |
| Reporting         | **ExtentReports**     |
| Remote Execution  | **Selenium Grid**     |
| CI/CD             | **Jenkins**           |

## Folder Structure
```text
restassured-petstore/
├── assets/                         # Static assets
├── logs/                           # Generated Log4j2 log files from test executions
├── reports/                        # Test execution reports (ExtentReports output)
├── src/
│   ├── main/
│   │   ├── java/com/earlbertmercado/restassured/petstore/
│   │   │   ├── clients/            # API client classes responsible for sending HTTP requests
│   │   │   ├── config/             # Configuration loaders
│   │   │   ├── endpoints/          # Centralized API endpoint path definitions
│   │   │   ├── payloads/           # Request and response POJOs (Lombok-enabled models)
│   │   │   └── utils/              # Reusable utilities (data providers, validators, report manager)
│   │   └── resources/
│   │       ├── schemas/            # JSON schema files for response validation
│   │       ├── config.properties   # Environment and framework-level configuration
│   │       └── log4j2.xml          # Log4j2 logging configuration
│   └── test/
│       ├── java/com/earlbertmercado/restassured/petstore/
│       │   ├── base/               # Base test classes (common setup, teardown, specs)
│       │   └── tests/              # TestNG test classes grouped by API functionality
│       └── resources/
│           ├── testdata/           # External test data for data-driven testing
│           └── testng/             # TestNG suite XML files and execution configurations
├── Jenkinsfile                     # Jenkins pipeline definition for CI execution
└── pom.xml                         # Maven project configuration and dependency management
```

## Prerequisites
 - Java 21 or higher
 - Maven 3.6+
 - IDE (IntelliJ IDEA, Eclipse, or VS Code)

## Installation
1. ### Clone the repository
    ```bash
    git clone https://github.com/earlbertmercado/restassured-petstore.git
    cd restassured-petstore
    ```
2. ### Import as Maven project
    ```bash
    mvn clean install
    ```
3. ### Update configurations (if needed) in `src/main/resources/config.properties`
4. ### Add test data in `src/test/resources/testdata/PetStoreTestData.xlsx`

## Running Tests

### Running all tests:
```bash
mvn clean test
```

### Run specific test class:
```bash
mvn test -Dtest=PetTest
```

### Run with specific test suite:
```bash
mvn test -DsuiteXmlFile=testng.xml
```

### Running from IDE:
 - Right-click on testng.xml and click run
 - Or run PetTest.java directly

## Reports
After test execution, detailed HTML reports are available at:
```bash
reports/PetStore_Test_Report.html
```

## Sample log output:
```text
[2026-01-04 00:13:34] INFO  ReportManager - Test started: testDeletePet
[2026-01-04 00:13:34] INFO  ReportManager - Deleting pet 1001
[2026-01-04 00:13:34] INFO  BaseClient - Executing DELETE request to: /pet/{petId}
[2026-01-04 00:13:36] INFO  ReportManager - Response: {"code":200,"type":"unknown","message":"1001"}
[2026-01-04 00:13:36] INFO  ReportManager - PASS: Test passed: testDeletePet
[2026-01-04 00:13:36] INFO  ReportManager - Completed test: testDeletePet
[2026-01-04 00:13:36] INFO  ReportManager - Test Suite Completed
[2026-01-04 00:13:36] INFO  ReportManager - Extent report flushed
```