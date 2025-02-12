# **Centimate API**

The **Centimate API** is a Spring Boot-based expense tracker designed to help users manage their financial transactions. It provides secure user authentication with JWT and endpoints for expense management.

**Note**: The API is temporarily available through the following URL: [https://centimate.onrender.com](https://centimate.onrender.com)

---

## **Features**
- User authentication with signup and login.
- Expense tracking with CRUD operations.
- Expense categorization (e.g., GROCERIES, HEALTH, LEISURE).
- Integration with MySQL.
- Dockerized for deployment and management.

---

## **Setup Instructions**

### 1. **Clone the Repository**
```sh
git clone <repository-url>
cd centimate-api
```

### **2.Configure Environment Variables**
Create a .env file in the project root with the following:
```sh
MYSQL_DATABASE=expensedb
MYSQL_PASSWORD=secret
```

### **3. Build and Run the Application**
```sh
make build
make run
```

---

## **Makefile Commands**
| Command      | Description                                           |
|--------------|-------------------------------------------------------|
| `make build` | Build the application and Docker image.               |
| `make run`   | Start the application and services using Docker Compose. |
| `make test`  | Run all tests with Maven.                             |
| `make stop`  | Stop the Docker Compose services.                     |
| `make clean` | Clean project artifacts and remove Docker containers/volumes. |
| `make logs`  | View the logs for the application service.            |

---

## **API Usage Overview**

To use the Centimate API, follow these steps:

1. **Signup:** Register a new user via `/auth/signup`.
2. **Login:** Obtain an authentication token via `/auth/login`.
3. **Authenticate:** Include the token in your requests as a Bearer Token.
4. **Access Endpoints:** Use the available endpoints to manage expenses.

For detailed API endpoint information, refer to the [API Documentation](./API.md).

---


## **Testing**
Run the following command to execute tests:
```sh
make test
```
---

## **Stopping and Cleaning**
To stop the services:
```sh
make stop
```

To stop and remove all containers, volumes, and networks:
```sh
make clean
```

