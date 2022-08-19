--liquibase formatted sql
--changeset <alcordya>:<add-products>

INSERT INTO products (title, price) VALUES ('iPhone 7', 700);
INSERT INTO products (title, price) VALUES ('iPhone 7', 710);
INSERT INTO products (title, price) VALUES ('iPhone 7', 720);
INSERT INTO products (title, price) VALUES ('iPhone 7', 730);
INSERT INTO products (title, price) VALUES ('iPhone 7', 740);
INSERT INTO products (title, price) VALUES ('iPhone 8', 760);
INSERT INTO products (title, price) VALUES ('iPhone 8', 770);
INSERT INTO products (title, price) VALUES ('iPhone 8', 780);
INSERT INTO products (title, price) VALUES ('iPhone 8', 790);
INSERT INTO products (title, price) VALUES ('iPhone X', 1000);
INSERT INTO products (title, price) VALUES ('iPhone X', 1100);
INSERT INTO products (title, price) VALUES ('iPhone X', 1200);
INSERT INTO products (title, price) VALUES ('iPhone X', 1300);
INSERT INTO products (title, price) VALUES ('iPhone X', 1400);
INSERT INTO products (title, price) VALUES ('iPhone X', 1500);
INSERT INTO products (title, price) VALUES ('iPhone 11', 1300);
INSERT INTO products (title, price) VALUES ('iPhone 11', 1400);
INSERT INTO products (title, price) VALUES ('iPhone 11', 1500);
INSERT INTO products (title, price) VALUES ('iPhone 11', 1600);
INSERT INTO products (title, price) VALUES ('iPhone 12', 1400);
INSERT INTO products (title, price) VALUES ('Samsung S20', 700);
INSERT INTO products (title, price) VALUES ('Samsung S20', 710);
INSERT INTO products (title, price) VALUES ('Samsung S20', 720);
INSERT INTO products (title, price) VALUES ('Samsung S20', 730);
INSERT INTO products (title, price) VALUES ('Samsung S20', 740);
INSERT INTO products (title, price) VALUES ('Samsung S21', 760);
INSERT INTO products (title, price) VALUES ('Samsung S21', 770);
INSERT INTO products (title, price) VALUES ('Samsung S21', 780);
INSERT INTO products (title, price) VALUES ('Samsung S21', 790);
INSERT INTO products (title, price) VALUES ('Samsung S22', 1000);
INSERT INTO products (title, price) VALUES ('Samsung S22', 1100);
INSERT INTO products (title, price) VALUES ('Samsung S22', 1200);
INSERT INTO products (title, price) VALUES ('Samsung S22', 1300);
INSERT INTO products (title, price) VALUES ('Samsung S22', 1400);
INSERT INTO products (title, price) VALUES ('Samsung S22', 1500);
INSERT INTO products (title, price) VALUES ('Samsung S23', 1300);
INSERT INTO products (title, price) VALUES ('Samsung S23', 1400);
INSERT INTO products (title, price) VALUES ('Samsung S23', 1500);
INSERT INTO products (title, price) VALUES ('Samsung S23', 1600);
INSERT INTO products (title, price) VALUES ('Samsung S23', 1400);

--rollback DELETE FROM products;