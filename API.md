**Centimate API Endpoints Documentation**
=========================================

This document provides detailed information about the available API endpoints in the Centimate API. Users must authenticate using a JWT token to access the protected endpoints.

* * * * *

**Authentication Endpoints**
----------------------------

### **POST** `api/v1/auth/signup`

-   **Description:** Registers a new user.
-   **Request Body:**

    ```
    {
      "username": "your-username",
      "password": "your-password"
    }

    ```

-   **Response:** User created confirmation.

* * * * *

### **POST** `api/v1/auth/login`

-   **Description:** Authenticates the user and returns a token.

-   **Request Body:**

    ```
    {
      "username": "your-username",
      "password": "your-password"
    }

    ```

-   **Response:**

    ```
    {
      "token": "your-auth-token",
      "expiresIn": "expiration-time"
    }

    ```

-   **Note:** Include the returned token in the `Authorization` header for subsequent requests:

    ```
    Authorization: Bearer <your-auth-token>

    ```

* * * * *

**Expense Management Endpoints**
--------------------------------

### **GET** `api/v1/expenses`

-   **Description:** Retrieves a paginated list of expenses.

-   **Query Parameters:**

    -   `page` (optional): Page number.
    -   `size` (optional): Number of items per page.
    -   `sort` (optional): Field to sort by.
    -   `order` (optional): Sort order (`asc` or `desc`).
-   **Response:** A list of expenses with pagination details.

* * * * *

### **POST** `api/v1/expenses`

-   **Description:** Creates a new expense.

-   **Request Body:**

    ```
    {
      "title": "Expense Title",
      "amount": 100.0,
      "category": "GROCERIES",
      "date": "2025-02-03",
      "notes": "Optional note"
    }

    ```

-   **Valid Categories:**

    -   GROCERIES
    -   LEISURE
    -   ELECTRONICS
    -   UTILITIES
    -   CLOTHING
    -   HEALTH
    -   OTHERS
-   **Response:** The created expense.

* * * * *

### **PUT** `api/v1/expenses/{id}`

-   **Description:** Updates an existing expense by ID.
-   **Request Body:** (Same as POST)
-   **Response:** The updated expense or an error message.

* * * * *

### **DELETE** `api/v1/expenses/{id}`

-   **Description:** Deletes an expense by ID.
-   **Response:** Success or error message.

* * * * *

**Notes:**
----------

-   Ensure that all requests (except signup and login) include the `Authorization` header with the Bearer token.
-   Use valid categories when creating or updating expenses to avoid validation errors.