## Simple Order Management System

This is a basic Order Management System built with Java and JDBC, designed to showcase fundamental backend development concepts. It provides a console-based interface for managing customers, products, orders, and a simple shopping cart.

![](https://github.com/mert-ergun/OrderManagementApplication/blob/main/img.png?raw=true)

**Features:**

* **Customer Management:** Add, view, update, and delete customer records.
* **Product Management:** Add, view, update, and delete product records, including stock management.
* **Order Management:** Create new orders, associating customers and products.
* **Shopping Cart:** Add products to a cart and generate orders from the cart.
* **Database Integration:** Utilizes PostgreSQL for data persistence.
* **User Authentication:** Simple login system for accessing the application.

**Technologies Used:**

* Java
* JDBC (Java Database Connectivity)
* PostgreSQL
* Swing (for user interface)

**Project Structure:**

* **business:** Contains the business logic controllers for managing entities (Customer, Product, Order, Cart).
* **core:** Contains utility classes for database connection and common functions.
* **dao:**  Data Access Objects (DAOs) responsible for database interactions.
* **entity:**  Represents the application's data models.
* **view:**  Contains Swing-based views for user interaction (Login, Dashboard, etc.).

**Getting Started:**

1. **Clone the repository:** `git clone https://github.com/your-username/order-management-java.git`
2. **Database Setup:**
    - Create a PostgreSQL database.
    - **Update the `config.properties` file with your database credentials.**
    - **Execute the SQL script (`schema.sql`) located in the root directory of the project to create the necessary tables.**
3. **Compile and Run:** Compile and run the `App.java` file to launch the application.

**Future Enhancements:**

* Implement a more robust user authentication and authorization system.
* Add support for order history and reporting.
* Improve the user interface with a more modern and user-friendly design.
* Implement data validation and error handling.
* Explore using a framework like Spring Boot for easier configuration and dependency management.
