# Simple API Server (Spring Boot)

A lightweight Java-based REST API using Spring Boot for user registration, login, and post management with in-memory storage and JWT-based authentication.

## Features

- User registration & login
- JWT authentication
- Post creation, liking, deletion, and listing
- In-memory storage (no DB)
- Logging with Log4j2
- REST API testing via Postman

## Tech Stack

- Java 17
- Spring Boot 3.x
- JJWT (Java JWT)
- Log4j2

## Getting Started

### Prerequisites

- Java 17 or later
- Maven

### Clone and Run

```bash
git clone <repo-url>
cd api-server-design-app
mvn spring-boot:run
```

### Build Jar

```bash
mvn clean package
java -jar target/backend-0.0.1-SNAPSHOT.jar
```

Server will run at: `http://localhost:8080`

## Authentication

All protected routes require a JWT token in the `Authorization` header:

```
Authorization: Bearer <token>
```

## API Endpoints

### 1. Register

`POST /api/register`

```json
{
  "username": "user1",
  "password": "pass123"
}
```

### 2. Login

`POST /api/login`

```json
{
  "username": "user1",
  "password": "pass123"
}
```

**Response:** JWT Token

### 3. Create Post

`POST /api/posts` **Headers:** Authorization

```json
{
  "content": "My first post!"
}
```

### 4. List Posts

`GET /api/posts` **Headers:** Authorization

### 5. Like Post

`POST /api/posts/{id}/like` **Headers:** Authorization

### 6. Delete Post

`DELETE /api/posts/{id}` **Headers:** Authorization

## Testing with Postman

1. Import the Postman collection: [api-server-postman-collection.json](./API Server App Collection.json)
2. Register & login to get token
3. Use `{{jwt_token}}` in Authorization headers

## Logs

Logs are stored in `logs/application.log` with Log4j2. Adjust level in `application.properties`.

## Sample Credentials

- username: `user1`
- password: `pass123`

## License

MIT License

