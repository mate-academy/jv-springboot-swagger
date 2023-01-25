--liquibase formatted sql
--changeset <mykolaskrypka>: <create-products-table>

INSERT INTO products (title, price) VALUES ('iPhone11', 699);
INSERT INTO products (title, price) VALUES ('iPhone13', 875);
INSERT INTO products (title, price) VALUES ('iPhone14', 1135);
INSERT INTO products (title, price) VALUES ('iPhone11', 725);
INSERT INTO products (title, price) VALUES ('iPhone11', 795);

--rollback DELETE FROM products;