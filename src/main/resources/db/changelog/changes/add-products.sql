--liquibase formatted sql
--changeset <bevz>:<create-products-table>

INSERT INTO products (title, price) VALUES ('iPhone X', 599);
INSERT INTO products (title, price) VALUES ('iPhone X1', 550);
INSERT INTO products (title, price) VALUES ('iPhone aX', 345);
INSERT INTO products (title, price) VALUES ('iPhone bX1', 4560);
INSERT INTO products (title, price) VALUES ('aiPhone X', 599);
INSERT INTO products (title, price) VALUES ('biPhone X1', 550);
INSERT INTO products (title, price) VALUES ('iPhone X', 599);
INSERT INTO products (title, price) VALUES ('diPhone X1', 550);
INSERT INTO products (title, price) VALUES ('fiPhone aX', 345);
INSERT INTO products (title, price) VALUES ('iPhone bX1', 4560);
INSERT INTO products (title, price) VALUES ('aiPhone X', 599);
INSERT INTO products (title, price) VALUES ('biPhone X1', 550);
INSERT INTO products (title, price) VALUES ('iPhone X', 599);
INSERT INTO products (title, price) VALUES ('iPhone X1', 550);
INSERT INTO products (title, price) VALUES ('iPhone aX', 345);
INSERT INTO products (title, price) VALUES ('iPhone bX1', 4560);
INSERT INTO products (title, price) VALUES ('aiPhone X', 599);
INSERT INTO products (title, price) VALUES ('biPhone X1', 550);
INSERT INTO products (title, price) VALUES ('iPhone X', 599);
INSERT INTO products (title, price) VALUES ('tiPhone X1', 10);
INSERT INTO products (title, price) VALUES ('iPhone aX', 25);
INSERT INTO products (title, price) VALUES ('iPhone bX1', 4560);
INSERT INTO products (title, price) VALUES ('aiPhone X', 599);
INSERT INTO products (title, price) VALUES ('biPhone X1', 550);
INSERT INTO products (title, price) VALUES ('iPhone X', 599);
INSERT INTO products (title, price) VALUES ('iPhone X1', 550);
INSERT INTO products (title, price) VALUES ('iPhone aX', 345);
INSERT INTO products (title, price) VALUES ('iPhone bX1', 4560);
INSERT INTO products (title, price) VALUES ('aiPhone X', 599);
INSERT INTO products (title, price) VALUES ('biPhone X1', 550);

--rollback DELETE FROM products;
