--creating the database
CREATE DATABASE push_down_process;

--connecting to the database as admin
\c push_down_process

CREATE  TYPE invoice_status AS ENUM('DRAFT', 'CONFIRMED', 'PAID');

CREATE TABLE if not exists invoice (
    id SERIAL PRIMARY KEY,
    customer_name VARCHAR(250) NOT NULL,
    status invoice_status
);

CREATE TABLE if not exists invoice_line (
    id SERIAL PRIMARY KEY,
    invoice_id INT NOT NULL REFERENCES invoice(id),
    label VARCHAR(250) NOT NULL,
    quantity INT NOT NULL,
    unit_price NUMERIC(10,2) NOT NULL
);

INSERT INTO invoice (customer_name, status) VALUES
                                                ('Alice', 'CONFIRMED'),
                                                ('Bob', 'PAID'),
                                                ('Charlie', 'DRAFT');

INSERT INTO invoice_line (invoice_id, label, quantity, unit_price) VALUES
                                                                       (1, 'Produit A', 2, 100),
                                                                       (1, 'Produit B', 1, 50),
                                                                       (2, 'Produit A', 5, 100),
                                                                       (2, 'Produit C', 1, 200),
                                                                       (3, 'Produit B', 3, 50);

CREATE TABLE tax_config (
    id SERIAL PRIMARY KEY,
    label VARCHAR(250) NOT NULL,
    rate NUMERIC(5,2) NOT NULL
);

INSERT INTO tax_config (label, rate) VALUES
                                         ('TVA STANDAR', 20);


