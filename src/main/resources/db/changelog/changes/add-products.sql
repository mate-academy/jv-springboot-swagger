--liquibase formatted sql
--changeset <Andrii>:<create-products-table>

INSERT INTO products (title, price) VALUES ('iPhone X', 1000);
INSERT INTO products (title, price) VALUES ('iPhone 2', 1001);
INSERT INTO products (title, price) VALUES ('iPhone 3', 1002);
INSERT INTO products (title, price) VALUES ('iPhone 4', 1003);
INSERT INTO products (title, price) VALUES ('iPhone 5', 1004);
INSERT INTO products (title, price) VALUES ('iPhone 6', 1005);
INSERT INTO products (title, price) VALUES ('iPhone 7', 1006);
INSERT INTO products (title, price) VALUES ('iPhone 7s', 1007);
INSERT INTO products (title, price) VALUES ('iPhone 8', 1008);
INSERT INTO products (title, price) VALUES ('iPhone 8s', 1009);
INSERT INTO products (title, price) VALUES ('iPhone X', 1010);
INSERT INTO products (title, price) VALUES ('iPhone Xr', 1011);
INSERT INTO products (title, price) VALUES ('iPhone Xs', 1012);
INSERT INTO products (title, price) VALUES ('iPhone XsMax', 1013);
INSERT INTO products (title, price) VALUES ('iPhone XI', 1014);
INSERT INTO products (title, price) VALUES ('iPhone XIpro', 1015);
INSERT INTO products (title, price) VALUES ('iPhone XImini', 1016);
INSERT INTO products (title, price) VALUES ('iPhone XII', 1017);
INSERT INTO products (title, price) VALUES ('iPhone XII', 1018);

--rollback DELETE FROM products;