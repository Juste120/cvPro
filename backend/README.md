# CVPro Backend - REST API for Professional CV Generator

## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Architecture](#architecture)
- [Technical Stack](#technical-stack)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Configuration](#configuration)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Testing](#testing)
- [Deployment](#deployment)
- [Contributing](#contributing)

## Overview
CVPro is a comprehensive backend REST API for creating and managing professional resumes. It features a secure authentication system using JWT, complete CV management with visual customization, and high-quality PDF export with internationalization support.

### Why CVPro?
- **Robust Security**: JWT authentication, BCrypt hashed passwords.
- **Complete Customization**: Light/Dark themes, customizable colors.
- **Professional PDF Export**: High-quality PDF generation with i18n.
- **Clean Architecture**: Adherence to SOLID principles and Clean Architecture.
- **Modern MongoDB**: NoSQL for flexibility and performance.
- **Docker Ready**: Simplified deployment with Docker Compose.

## Features

### Authentication & Security
- Registration and login with JWT.
- Secure tokens with configurable expiration.
- Passwords hashed with BCrypt (automatic salt).
- Role management (USER, ADMIN).
- Configurable CSRF and CORS protection.

### User Management
- Full user CRUD operations.
- User profile with preferences.
- Modification of preferences (language, theme, default color).
- Secure password change.
- User administration (ADMIN role).

### CV Management
- Unlimited CV creation per user.
- **Comprehensive Sections**:
  - Personal Information
  - Professional Summary
  - Work Experience (with achievements)
  - Education
  - Technical Skills (by category and level)
  - Languages (4 levels)
  - Volunteer Activities
  - Interests
- **Visual Customization**:
  - Light/Dark Themes
  - Customizable Primary Color
  - Customizable Accent Color
- **Operations**:
  - Full or partial modification (styling only).
  - Deletion with ownership verification.
  - CV duplication.

### PDF Export
- High-quality PDF generation with iText.
- Adherence to the selected theme (Light/Dark).
- Application of custom colors.
- Internationalization (FR/EN) via Accept-Language header.
- Automatic filename generation: `CV_[Date].pdf`.
- Appropriate HTTP headers for file download.

### Internationalization
- French and English language support.
- Separate resource files for translations.
- Automatic language detection via `Accept-Language` header.
- Translation of labels in the generated PDF.

## Architecture
The project follows a layered architecture with a clear separation of concerns:

```
┌─────────────────────────────────────────┐
│         Controllers (REST API)           │
│  - Input Validation (DTOs)              │
│  - HTTP Response Management             │
└─────────────────┬───────────────────────┘
                  │
┌─────────────────▼───────────────────────┐
│       Services (Business Logic)         │
│  - Business Logic                       │
│  - Transactions                         │
│  - Authorization Checks                 │
└─────────────────┬───────────────────────┘
                  │
┌─────────────────▼───────────────────────┐
│        Repositories (Data Access)        │
│  - MongoDB Interactions                 │
│  - Custom Queries                       │
└─────────────────┬───────────────────────┘
                  │
┌─────────────────▼───────────────────────┐
│           MongoDB Database               │
│  - Collections: users, cvs               │
│  - Indexes for performance              │
└─────────────────────────────────────────┘
```

### Cross-Cutting Concerns
- **Security**: JWT, authentication filters.
- **Mappers**: Document ↔ DTO conversion.
- **Exceptions**: Global error handling.
- **Configuration**: MongoDB, Security, I18n.

## Technical Stack

### Backend
- **Java 17** - Programming Language
- **Spring Boot 3.2.0** - Core Framework
- **Spring Data MongoDB** - Data Access
- **Spring Security** - Security and Authentication
- **JWT (jjwt 0.12.3)** - Authentication Tokens
- **MapStruct** - Automatic Mapping
- **Lombok** - Boilerplate Reduction
- **iText 5.5.13** - PDF Generation

### Database
- **MongoDB 7.0** - NoSQL Database
- **Mongo Express** - Web-based administration interface

### DevOps & Tools
- **Docker & Docker Compose** - Containerization
- **Maven** - Dependency Management
- **JUnit 5** - Unit Testing
- **Mockito** - Mocking for tests
- **Testcontainers** - Integration Testing

### Documentation
- **SpringDoc OpenAPI 3** - Interactive API documentation (Swagger)

## Prerequisites
Before you begin, ensure you have the following installed:
- Java 17 or higher
- Maven 3.8+
- Docker and Docker Compose
- Git
- A code editor (IntelliJ IDEA, VS Code, Eclipse)

### Version Check
```bash
java -version    # Java 17+
mvn -version     # Maven 3.8+
docker --version # Docker 20.10+
docker-compose --version
```

## Installation

### 1. Clone the Project
```bash
git clone https://github.com/your-username/cvpro-backend.git
cd cvpro-backend
```

### 2. Create the .env file
Copy the following content into a `.env` file at the root of the project:
```dotenv
# MongoDB Root Credentials
MONGO_ROOT_USERNAME=admin
MONGO_ROOT_PASSWORD=SecurePassword123!
MONGO_PORT=27017

# Mongo Express (Web Interface)
MONGO_EXPRESS_PORT=8081
MONGO_EXPRESS_USERNAME=admin
MONGO_EXPRESS_PASSWORD=admin123

# Application MongoDB Connection
MONGO_DB_NAME=cvpro
MONGO_APP_USERNAME=cvpro_user
MONGO_APP_PASSWORD=CvPro2025!

# JWT Configuration
JWT_SECRET=YourSuperSecretJWTKeyThatShouldBeVeryLongAndSecure2025CvPro!
JWT_EXPIRATION=86400000

# Spring Profile
SPRING_PROFILES_ACTIVE=dev
SERVER_PORT=8080
```
**IMPORTANT**: Never commit the `.env` file! It is already included in `.gitignore`.

### 3. Create the MongoDB Initialization Directory
```bash
mkdir -p mongo-init
```
Create the file `mongo-init/01-init-db.js` with the content of the provided initialization script.

## Configuration

### Spring Profiles
The project uses three profiles:
- `dev` (default): Local development
- `test`: Unit and integration testing
- `prod`: Production

### Environment Variables
All sensitive configurations are externalized in `.env`:

| Variable              | Description           | Default Value      |
|-----------------------|-----------------------|--------------------|
| MONGO_ROOT_USERNAME   | MongoDB Admin User    | `admin`            |
| MONGO_ROOT_PASSWORD   | Admin Password        | `SecurePassword123!`|
| MONGO_APP_USERNAME    | Application User      | `cvpro_user`       |
| MONGO_APP_PASSWORD    | Application Password  | `CvPro2025!`       |
| JWT_SECRET            | JWT Secret            | (long key)         |
| JWT_EXPIRATION        | Token Expiration (ms) | `86400000` (24h)   |
| SERVER_PORT           | Spring Boot Port      | `8080`             |

## Usage

### Option 1: With Docker Compose (Recommended)

#### 1. Start MongoDB
```bash
docker-compose up -d
```
This starts:
- MongoDB on port `27017`
- Mongo Express on port `8081`

#### 2. Verify MongoDB is Ready
```bash
docker-compose ps
docker-compose logs mongodb
```
You should see: `Database "cvpro" initialized successfully`

#### 3. Access Mongo Express
Open your browser to: http://localhost:8081
- **Username**: `admin`
- **Password**: `admin123`

#### 4. Compile and Run the Application
```bash
mvn clean install
mvn spring-boot:run
```

#### 5. Verify the Application is Running
```bash
curl http://localhost:8080/actuator/health
```
Expected response: `{"status":"UP"}`

### Option 2: Without Docker (Local MongoDB)
If you have MongoDB installed locally:
```bash
# 1. Start MongoDB
mongod --dbpath /path/to/data

# 2. Create the database
mongosh
> use cvpro
> db.createUser({user: "cvpro_user", pwd: "CvPro2025!", roles: [{role: "readWrite", db: "cvpro"}]})

# 3. Run the application
mvn spring-boot:run
```

## API Endpoints

### Swagger UI
Interactive documentation is available at:
**URL**: http://localhost:8080/swagger-ui.html

![Swagger UI](./public/cvPro_swagger.png)

### Authentication (Public)

| Method | Endpoint             | Description  |
|--------|----------------------|--------------|
| POST   | `/api/auth/register` | Registration |
| POST   | `/api/auth/login`    | Login        |

**Example - Registration**:
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "password": "password123"
  }'
```
Response:
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9...",
  "type": "Bearer",
  "email": "john.doe@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "role": "USER"
}
```

### Users (Authenticated)

| Method | Endpoint                    | Description         | Role  |
|--------|-----------------------------|---------------------|-------|
| GET    | `/api/users/me`             | User Profile        | USER  |
| PUT    | `/api/users/me`             | Modify Profile      | USER  |
| PATCH  | `/api/users/me/preferences` | Modify Preferences  | USER  |
| PATCH  | `/api/users/me/password`    | Change Password     | USER  |
| DELETE | `/api/users/me`             | Delete Account      | USER  |
| GET    | `/api/users`                | List Users          | ADMIN |
| GET    | `/api/users/{id}`           | User Details        | ADMIN |
| DELETE | `/api/users/{id}`           | Delete User         | ADMIN |

**Example - Get Profile**:
```bash
curl -X GET http://localhost:8080/api/users/me \
  -H "Authorization: Bearer <your_token>"
```

### CV (Authenticated)

| Method | Endpoint                  | Description       |
|--------|---------------------------|-------------------|
| GET    | `/api/cvs`                | List My CVs       |
| POST   | `/api/cvs`                | Create CV         |
| GET    | `/api/cvs/{id}`           | CV Details        |
| PUT    | `/api/cvs/{id}`           | Modify Full CV    |
| PATCH  | `/api/cvs/{id}/styling`   | Modify Styling    |
| DELETE | `/api/cvs/{id}`           | Delete CV         |
| POST   | `/api/cvs/{id}/duplicate` | Duplicate CV      |

**Example - Create a CV**:
```bash
curl -X POST http://localhost:8080/api/cvs \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Full Stack Developer CV",
    "personalInfo": {
      "fullName": "John Doe",
      "jobTitle": "Full Stack Developer",
      "email": "john.doe@example.com",
      "phone": "+33 6 12 34 56 78"
    },
    "summary": "Passionate developer with 5 years of experience",
    "styling": {
      "theme": "LIGHT",
      "primaryColor": "#3B82F6",
      "accentColor": "#10B981"
    }
  }'
```

### Export (Authenticated)

| Method | Endpoint                       | Description    |
|--------|--------------------------------|----------------|
| GET    | `/api/export/pdf/{cvId}?lang=en` | Download PDF   |

**Example - Export PDF**:
```bash
curl -X GET "http://localhost:8080/api/export/pdf/cv123?lang=en" \
  -H "Authorization: Bearer <token>" \
  -H "Accept-Language: en" \
  --output CV_John_Doe.pdf
```

## Testing

### Run All Tests
```bash
mvn test
```

### Unit Tests Only
```bash
mvn test -Dtest=*Test
```

### Integration Tests
```bash
mvn test -Dtest=*IT
```

### Code Coverage
```bash
mvn test jacoco:report
```
The report is generated in `target/site/jacoco/index.html`.

## Deployment

### Production with Docker

**Build the Application**:
```bash
mvn clean package -DskipTests
```

**Create Docker Image**:
```Dockerfile
# Dockerfile
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY target/cvpro-backend-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```
```bash
docker build -t cvpro-backend:1.0.0 .
```

**Run with docker-compose**:
Add the service to `docker-compose.yml`:
```yaml
  backend:
    build: .
    container_name: cvpro-backend
    ports:
      - "8080:8080"
    environment:
      SPRING_PROFILES_ACTIVE: prod
      MONGODB_URI: mongodb://cvpro_user:CvPro2025!@mongodb:27017/cvpro
    depends_on:
      - mongodb
    networks:
      - cvpro-network
```
```bash
docker-compose up -d
```

### Production Environment Variables
Create a `.env.production` file:
```dotenv
MONGO_ROOT_PASSWORD=<strong_password>
MONGO_APP_PASSWORD=<strong_password>
JWT_SECRET=<very_long_random_secret>
SPRING_PROFILES_ACTIVE=prod
```

## Additional Documentation

### Project Structure
```
cvpro-backend/
├── src/main/java/com/cvpro/
│   ├── config/                 # Configurations
│   ├── controller/             # REST Controllers
│   ├── document/               # MongoDB Documents
│   │   └── embedded/           # Embedded Classes
│   ├── dto/                    # Data Transfer Objects
│   │   ├── request/
│   │   └── response/
│   ├── enums/                  # Enumerations
│   ├── exception/              # Error Handling
│   ├── mapper/                 # Mappers
│   ├── repository/             # MongoDB Repositories
│   ├── security/               # JWT Security Configuration
│   ├── service/                # Business Services
│   │   └── impl/
│   └── CvProApplication.java
├── src/main/resources/
│   ├── application.yml
│   ├── application-dev.yml
│   ├── application-test.yml
│   ├── application-prod.yml
│   └── i18n/
│       ├── messages_en.properties
│       └── messages_fr.properties
├── src/test/java/              # Tests
├── docker-compose.yml
├── .env
├── .gitignore
├── pom.xml
└── README.md
```

### Best Practices Used
- **Layer Separation**: Controller → Service → Repository
- **Immutable DTOs**: Java 17 Records
- **Dual Validation**: DTOs + Services
- **Centralized Error Handling**: GlobalExceptionHandler
- **Structured Logging**: SLF4J with appropriate levels
- **Transactions**: `@Transactional` on modification methods
- **Security**: JWT, BCrypt, ownership verification
- **Testing**: Unit + Integration

## Contributing
Contributions are welcome! Here's how to contribute:
1. Fork the project
2. Create a branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

### Contribution Rules
- Code formatted according to Java conventions
- Unit tests for new features
- Updated documentation
- Clear and descriptive commit messages

## Author
- **Email**:pakoujuste2019@gmail.com
- **GitHub**: Juste_sama

## Acknowledgements
- Spring Boot Team
- MongoDB Team
- iText Team
- The Open Source Community

