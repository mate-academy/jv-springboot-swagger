--liquibase formatted sql
--changeset <losnazar>:<insert-products-table>

INSERT INTO products (title, price) VALUES ('iPhone 12', 1250);
INSERT INTO products (title, price) VALUES ('iPhone X', 550);
INSERT INTO products (title, price) VALUES ('iPhone 11', 750);
INSERT INTO products (title, price) VALUES ('iPhone 7', 570);
INSERT INTO products (title, price) VALUES ('iPhone 6', 250);
INSERT INTO products (title, price) VALUES ('iPhone 5S', 720);
INSERT INTO products (title, price) VALUES ('iPhone 13', 650);
INSERT INTO products (title, price) VALUES ('iPhone 14', 350);
INSERT INTO products (title, price) VALUES ('iPhone 4', 450);
INSERT INTO products (title, price) VALUES ('iPhone X', 650);
INSERT INTO products (title, price) VALUES ('Huawei P9 red', 650);
INSERT INTO products (title, price) VALUES ('Huawei P9 yellow', 950);
INSERT INTO products (title, price) VALUES ('Huawei P9 black', 1050);
INSERT INTO products (title, price) VALUES ('Xiaomi Redmi', 350);
INSERT INTO products (title, price) VALUES ('Huawei X3', 559);
INSERT INTO products (title, price) VALUES ('Huawei P7', 659);
INSERT INTO products (title, price) VALUES ('Huawei V2', 689);
INSERT INTO products (title, price) VALUES ('Huawei One', 690);
INSERT INTO products (title, price) VALUES ('Huawei P9 pink', 1450);
INSERT INTO products (title, price) VALUES ('Xiaomi Redmi S', 1250);
INSERT INTO products (title, price) VALUES ('Xiaomi Note', 1450);
INSERT INTO products (title, price) VALUES ('Xiaomi XC', 1050);
INSERT INTO products (title, price) VALUES ('Xiaomi Redmi 1', 950);
INSERT INTO products (title, price) VALUES ('Xiaomi Redmi 1', 850);
INSERT INTO products (title, price) VALUES ('Xiaomi Redmi 2 black', 350);
INSERT INTO products (title, price) VALUES ('Xiaomi Redmi 4 yellow', 790);
INSERT INTO products (title, price) VALUES ('Xiaomi Redmi BT', 1200);
INSERT INTO products (title, price) VALUES ('Xiaomi Redmi X', 1220);
INSERT INTO products (title, price) VALUES ('Xiaomi Redmi N', 1060);
INSERT INTO products (title, price) VALUES ('Samsung X5 silver', 1060);
INSERT INTO products (title, price) VALUES ('Samsung X5 gold', 1876);
INSERT INTO products (title, price) VALUES ('Samsung X5', 1234);
INSERT INTO products (title, price) VALUES ('Samsung X10', 567);
INSERT INTO products (title, price) VALUES ('Samsung X22', 960);
INSERT INTO products (title, price) VALUES ('Samsung A55', 1260);
INSERT INTO products (title, price) VALUES ('Samsung X5', 1110);
INSERT INTO products (title, price) VALUES ('Samsung X9', 1200);
INSERT INTO products (title, price) VALUES ('Samsung XG45', 980);
INSERT INTO products (title, price) VALUES ('Huawei 9S', 460);
INSERT INTO products (title, price) VALUES ('Samsung X6', 780);

--rollback DELETE FROM products;