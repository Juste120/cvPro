# 📄 cvPro — Professional Résumé Builder API

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3-brightgreen)
![MongoDB](https://img.shields.io/badge/MongoDB-7-green)
![Docker](https://img.shields.io/badge/Docker-Compose-blue)

REST API for creating, styling and exporting professional résumés.
Users sign up, build several CVs, customise their look, duplicate them, and download them as PDF.

## ✨ Features

- **JWT authentication**: registration, login, stateless sessions
- **CV management**: create, update, delete and duplicate résumés
- **Styling**: update a CV's layout and colours independently of its content
- **PDF export**: résumé generated server-side with iText
- **User profile**: preferences, password change, account deletion
- **Validation & error handling**: Bean Validation and centralised exceptions
- **Interactive documentation**: OpenAPI / Swagger UI

## 🛠️ Tech stack

| Technology | Usage |
|---|---|
| Java 17 + Spring Boot 3 | Backend |
| Spring Security + JJWT | Authentication |
| Spring Data MongoDB | Persistence |
| MapStruct + Lombok | DTO mapping |
| iText | PDF generation |
| Springdoc OpenAPI | API documentation |
| Docker Compose | MongoDB + Mongo Express |

## 🏗️ Project structure

```
backend/src/main/java/juste/backend/
├── config/        # Swagger and app configuration
├── controllers/   # REST endpoints
├── document/      # MongoDB documents
├── dtos/          # Request / response objects
├── enums/
├── exceptions/    # Custom exceptions + global handler
├── mappers/       # MapStruct mappers
├── repositories/  # Spring Data repositories
├── securite/      # JWT filter and security config
└── services/      # Business logic
```

## 🚀 Getting started

### Prerequisites

- Java 17+
- Docker

### 1. Start MongoDB

Create `backend/.env`:

```env
MONGO_ROOT_USERNAME=admin
MONGO_ROOT_PASSWORD=change-me
MONGO_DB_NAME=cvpro
```

```bash
cd backend
docker compose up -d
```

Mongo Express is available at http://localhost:8081.

### 2. Run the API

```bash
export MONGODB_URI="mongodb://<user>:<password>@localhost:27017/cvpro?authSource=cvpro"
export JWT_SECRET="a-secret-of-at-least-32-characters"
./mvnw spring-boot:run
```

The API runs at http://localhost:8080 and Swagger UI at http://localhost:8080/swagger-ui.html.

## 📚 Main endpoints

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/auth/register` | Create an account |
| `POST` | `/api/auth/login` | Log in and get a JWT |
| `GET` | `/api/cvs` | List my CVs |
| `POST` | `/api/cvs` | Create a CV |
| `GET` | `/api/cvs/{id}` | Get a CV |
| `PUT` | `/api/cvs/{id}` | Update a CV |
| `PATCH` | `/api/cvs/{id}/styling` | Update a CV's styling |
| `POST` | `/api/cvs/{id}/duplicate` | Duplicate a CV |
| `DELETE` | `/api/cvs/{id}` | Delete a CV |
| `GET` | `/api/export/pdf/{cvId}` | Download a CV as PDF |
| `GET` | `/api/users/me` | Get my profile |
| `PATCH` | `/api/users/me/password` | Change my password |

## 🧪 Tests

```bash
cd backend
./mvnw test
```

## 👤 Author

**PAKOU Komi Juste**: [Portfolio](https://portfolio.pakoujuste.com) · [LinkedIn](https://www.linkedin.com/in/komi-juste-pakou-941536314/) · [GitHub](https://github.com/Juste120)
