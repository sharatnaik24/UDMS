
# University Details Management System (UDMS)

## Overview

The **University Details Management System (UDMS)** is a web application designed to manage and streamline various administrative and academic operations within a university. The backend is built using the Java Spring Boot framework, providing a scalable and secure solution. Data is securely managed and stored in a MySQL database.

- **Admin Module**:
  - Create, read, update, and delete student, course, and professor records.
  - Access and manage comprehensive reports on university details.
  - Secure dashboard for overall university management.

- **Student Module**:
  - View available courses, marks, and attendance.
  - Provide feedback on courses and professors.
  - User-friendly interface for seamless navigation.

- **Professor Module**:
  - Manage attendance and enter student marks.
  - Access and review student feedback.
  - Intuitive layout for easy interaction.

- **Security**:
  - User authentication and authorization with JWT (JSON Web Token).
  - Role-based access control for admins, students, and professors.
  - Secure API endpoints with Spring Security.

- **Dynamic Data Handling**:
  - Responsive display of nested objects in tables with modal views.
  - Dynamic API response handling for various data types.

- **Testing**:
  - API validation with Postman.
  - Unit and integration tests using Mockito and JUnit.

## Tech Stack

### Backend

- **Framework**: Java Spring Boot
- **Database**: MySQL
- **Security**: Spring Security with JWT
- **Testing**: Postman, Mockito, JUnit

### Frontend

- **Languages**: HTML, CSS, JavaScript
- **Design**: Modern and stylized UI with gradients, vibrant colors, rounded elements, shadows, and hover animations.
- **Responsiveness**: Designed to work seamlessly on various devices.
- **User Interface**: 
  - Login and sign-up forms with reduced width and header relative to the page.
  - Profile management with username display fetched from `localStorage`.
  - Dynamic display of data based on user role and API responses.

## Getting Started

### Prerequisites

- Java 11 or higher
- Maven
- MySQL Server
- Web Browser (Chrome, Firefox, etc.)
- Postman (for testing)

### Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/unknownuuu/UDMS.git
   cd UDMS
   ```

2. Configure MySQL:

   - Install MySQL Server if not already installed.
   - Create a database named `udms_db`.
   - Create a user with appropriate permissions and set the password.

3. Set up environment variables:

   Create a `.env` file in the root directory and add the following:

   ```plaintext
   SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/udms_db
   SPRING_DATASOURCE_USERNAME=<username>
   SPRING_DATASOURCE_PASSWORD=<password>
   SPRING_JPA_HIBERNATE_DDL_AUTO=update
   ```

4. Build the project:

   ```bash
   mvn clean install
   ```

5. Run the application:

   ```bash
   mvn spring-boot:run
   ```
6. Access the Frontend:

   - Open a web browser and navigate to \`http://localhost:8080/\` to interact with the application.
     
### API Endpoints

The API is documented using Swagger. Once the application is running, you can access the API documentation at:

- [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### Testing

#### Postman

A Postman collection is included in the repository. Import `UDMS.postman_collection.json` into Postman to test the API endpoints.

#### Unit Tests

The project includes unit and integration tests using Mockito and JUnit. To run the tests, use the following command:

```bash
mvn test
```

## Security

The application uses Spring Security with JWT to secure API endpoints. Ensure that you configure proper security measures and handle sensitive data responsibly.

## Contributing

Contributions are welcome! Please open an issue or submit a pull request.

## Contact

If you have any questions or feedback, please reach out to us at `sharatnaik369@gmail.com`.
