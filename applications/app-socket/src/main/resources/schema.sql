USE inventory;

-- Create the Branch table
CREATE TABLE IF NOT EXISTS Branch (
    id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    location VARCHAR(255) NOT NULL
    );

-- Create the User (Employee) table with a foreign key to Branch
CREATE TABLE IF NOT EXISTS User (
    id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    lastname VARCHAR(150) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    role VARCHAR(50) NOT NULL,
    branch_id VARCHAR(50),
    FOREIGN KEY (branch_id) REFERENCES Branch(id) ON DELETE CASCADE
    );

-- Create the Product table with a foreign key to Branch
CREATE TABLE IF NOT EXISTS Product (
    id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    inventory_stock INT NOT NULL,
    price FLOAT NOT NULL,
    category VARCHAR(100) NOT NULL,
    branch_id VARCHAR(50),
    FOREIGN KEY (branch_id) REFERENCES Branch(id) ON DELETE CASCADE
    );
