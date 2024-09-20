-- Create the 'users' table
CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       email VARCHAR(255) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL
);

-- Create the 'customers' table
CREATE TABLE customers (
                           id SERIAL PRIMARY KEY,
                           name VARCHAR(255) NOT NULL,
                           type VARCHAR(255) NOT NULL, -- 'INDIVIDUAL' or 'CORPORATE'
                           phone VARCHAR(20),
                           email VARCHAR(255),
                           address TEXT
);

--Create an enum for the customer types
CREATE TYPE "CustomerType" AS ENUM ('INDIVIDUAL', 'CORPORATE');

--Alters the customer table to use the enum
ALTER TABLE customers
ALTER COLUMN type TYPE "CustomerType" USING type::"CustomerType";

-- Create the 'products' table
CREATE TABLE products (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          code VARCHAR(20) UNIQUE NOT NULL,
                          price INT NOT NULL,
                          stock INT NOT NULL
);

-- Create the 'carts' table
CREATE TABLE carts (
                       id SERIAL PRIMARY KEY,
                       product_id INT REFERENCES products(id) ON DELETE CASCADE
);

-- Create the 'orders' table
CREATE TABLE orders (
                        id SERIAL PRIMARY KEY,
                        customer_id INT REFERENCES customers(id) ON DELETE CASCADE,
                        product_id INT REFERENCES products(id) ON DELETE CASCADE,
                        price INT NOT NULL,
                        date DATE NOT NULL,
                        note TEXT
);