
-- Create Database
CREATE DATABASE IF NOT EXISTS shopdb;
USE shopdb;

-- Users Table
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL,
    is_admin TINYINT(1) DEFAULT 0
);

-- Products Table
CREATE TABLE IF NOT EXISTS products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DOUBLE NOT NULL,
    stock INT NOT NULL CHECK (stock >= 0)
);

-- Purchases Table
CREATE TABLE IF NOT EXISTS purchases (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL CHECK (quantity > 0),
    FOREIGN KEY (username) REFERENCES users(username) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

-- Insert initial data into the users table
INSERT INTO users (username, password, is_admin) VALUES 
VALUES ('admin', 'adminpass', true);

-- Insert initial data into the products table
INSERT INTO products (name, price, stock) VALUES 
('Dell XPS 13', 85000, 10),
('Logitech G502 HERO', 4000, 15),
('Corsair K70 RGB MK.2', 8000, 10);


-- Insert initial purchases for demonstration
INSERT INTO purchases (username, product_id, quantity) VALUES 

