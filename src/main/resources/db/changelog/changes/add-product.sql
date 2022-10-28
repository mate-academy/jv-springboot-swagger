--liquibase formatted sql
--changeset <losnazar>:<insert-products-table>
INSERT INTO products (title, price) VALUES ('Samsung A55', 1050);
--rollback DELETE FROM products;