# Employee Records System


# Read Me First
The following was discovered as part of building this project:

* The original package name 'com.employee.records.employee-records-system' is invalid and this project uses 'com.employee.records.employee_records_system' instead.

# Getting Started :

## Overview
The Employee Records System is a comprehensive application for managing employee information, roles, and audit logs. It incorporates role-based access control for HR Personnel, Managers, and Administrators, providing the appropriate permissions for each user type. The system is designed with a minimalist Swing-based UI and a backend powered by Spring Boot.

## Features
- **User Login**: Allows users to log in with email and password.
- **Role-Based Access Control**:
    - HR Personnel: Full CRUD operations on employee data.
    - Managers: View and update specific employee details within their department.
    - Administrators: Full system access, including user permissions and configuration.
- **CRUD Operations**:
    - Manage employees, roles, and users.
- **Audit Logs**: Logs changes to employee records, including who made the changes .
- **Minimalist UI**: Swing-based desktop application with intuitive design.

---
## Technology Stack
- **Backend**: Spring Boot, Hibernate, H2 Database
- **Frontend**: Java Swing
- **API Testing**: Postman
- **Build Tool**: Maven

---

## Setup Instructions

### Prerequisites
- Java 17+
- Maven 3.8+
- IDE: IntelliJ IDEA or Eclipse (Recommended)
- Postman (Optional, for testing API endpoints)

### Steps to Run the Application
1. Clone the repository:
   ```bash
   git clone https://github.com/oumaima-bkr/Employee-Records-Management-System.git
2. Navigate to the project directory:
   ```bash
   cd employee-records-system
3. Build the project using Maven:
    ```bash
   mvn clean install

4. Run the application:
   ```bash
   mvn spring-boot:run
   
5. Access the H2 Database Console (if needed):
    - URL: http://localhost:8080/h2-console
    - JDBC URL: jdbc:h2:mem:testDb
    - Username: sa
    - Password: password
    - 
6. Launch the Swing UI:
   Run the MainFrame class in the com.employee.records.employee_records_system.UI package.

## API Endpoints


## User Roles and Permissions
### HR Personnel
- Permissions: Full CRUD operations on employee data.
### Managers
- Permissions: View and update specific employee details within their department.
### Administrators
- Permissions: Full system access, including managing users and permissions.

## UI Features 
- Login Interface: Minimalist design for user authentication.
- Role Management: Add, edit, and delete roles.
- Employee Management: Full employee CRUD operations for authorized users.
- Audit Logs: View logs of all changes made to employee records.
- Application users : manage application users .


## Testing
### Postman Collection
Use the provided Postman collection to validate API endpoints. Import the collection.json file into Postman and update the base_url variable if necessary.
### Unit Testing
Run the unit tests included in the test package : mvn test

## Contributors

Eyomi - oumaima.bakri08@gmail.com
        
        
## Testing       


### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.4.1/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.4.1/maven-plugin/build-image.html)


