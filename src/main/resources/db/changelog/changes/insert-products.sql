--liquibase formatted sql
--changeset <olyaklipa>:<create-products-table>

INSERT INTO products (title, price) VALUES ('ProductB', 100);
INSERT INTO products (title, price) VALUES ('ProductW', 500);
INSERT INTO products (title, price) VALUES ('ProductA', 400);
INSERT INTO products (title, price) VALUES ('ProductT', 200);
INSERT INTO products (title, price) VALUES ('ProductC', 800);
INSERT INTO products (title, price) VALUES ('ProductR', 300);
INSERT INTO products (title, price) VALUES ('ProductS', 900);
INSERT INTO products (title, price) VALUES ('ProductR', 600);

--rollback DELETE FROM products;
