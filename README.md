# PCG Peripherals Shop Management System

This project is a console-based Java program that simulates a shop management system where users can:
- Log In / Sign Up
- Browse and purchase products
- View their purchase history
- Admin users can manage the product inventory

## **Technologies Used**
- **Java** (Core application logic)
- **MySQL** (Database for user data, product information, and purchases)
- **JDBC** (Java Database Connectivity)

## **Database Design**
The program uses a MySQL database named `shopdb` with the following tables:

1. **users**
   - `id`: Unique user ID (Primary Key)
   - `username`: User's login name
   - `password`: User's password
   - `is_admin`: Boolean flag (1 = admin, 0 = regular user)

2. **products**
   - `id`: Unique product ID (Primary Key)
   - `name`: Product name
   - `price`: Product price
   - `stock`: Quantity available in stock

3. **purchases**
   - `id`: Unique purchase record ID (Primary Key)
   - `username`: The buyer's username
   - `product_id`: References the product purchased
   - `quantity`: Quantity purchased

---

## **Key Functionalities**

### **User Management**
- **Sign Up**: New users can create an account.
- **Log In**: Existing users (including admins) can log in.

### **Product Management**
- **View Products**: All users can view available products.
- **Buy Products**:
  - Users can buy products by selecting the product ID and quantity.
  - The program validates stock availability and updates the database.

### **Purchase History**
- Users can view their past purchases.

### **Admin Features**
- Admin users have access to manage the product inventory:
  - Add new products
  - Update existing products
  - Remove products

---

## **Running the Project**

### **Prerequisites**
1. Install [MySQL Server](https://dev.mysql.com/downloads/mysql/).
2. Install [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html).
3. Ensure MySQL JDBC Driver is included in your classpath.

### **Setup Instructions**
1. Create a MySQL database named `shopdb`.
2. Execute the following SQL script to create the required tables:

```sql
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL,
    is_admin TINYINT(1) DEFAULT 0
);

CREATE TABLE products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DOUBLE NOT NULL,
    stock INT NOT NULL
);

CREATE TABLE purchases (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    FOREIGN KEY (username) REFERENCES users(username),
    FOREIGN KEY (product_id) REFERENCES products(id)
);
