# 💸**Centimate API**💸

The **Centimate API** is a Spring Boot-based expense tracker designed to help users manage their financial transactions. It provides secure user authentication with JWT and endpoints for expense management.

---

## **Features**
- User authentication with signup and login.
- Expense tracking with CRUD operations.
- Expense categorization (e.g., GROCERIES, HEALTH, LEISURE).
- Integration with PostgresSQL.
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
POSTGRES_DB=centimate_db
POSTGRES_USER=local
POSTGRES_PASSWORD=password
JWT_SECRET=<your-generated-secret>
```

**Important:**

-   Replace `<your-generated-secret>` with a securely generated key.
-   You can generate a secure key using:

    ```
    openssl rand -hex 32
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

**Trade-offs**
--------------

❌ **Expenses are not user-specific** -- All users can see all expenses.\
✅ **Solution:** Associate expenses with a user so each user only sees their own expenses.

❌ **Tests are incomplete and failing** -- The failures are due to a configuration issue, not a logic error.\
✅ **Solution:** Debug the test setup to ensure proper execution.

❌ **Users cannot create categories** -- Categories are predefined and cannot be customized.\
✅ **Solution:** Allow users to create and manage their own categories.

❌ **No logout endpoint** -- There's no way to invalidate a user's session.\
✅ **Solution:** Add a logout endpoint to properly handle session termination.

❌ **Expense DTO includes unnecessary details** -- The DTO returns notes in list views, making responses larger than necessary.\
✅ **Solution:** Use a shorter DTO for lists and include notes only in detailed views.

---

**Future Improvements**
-----------------------

**Total Spending API** -- Add an endpoint to calculate total spending over a selected period.

**Expense Visualization** -- Implement charts to display spending trends and insights.

**Budgeting Feature** -- Introduce a class to set budget limits, helping users track and manage expenses within predefined limits.

