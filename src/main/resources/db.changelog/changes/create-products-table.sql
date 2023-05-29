--liquibase formatted sql
-- changeset <vadimhodor>:<create-products-table>
DROP TABLE IF EXISTS products;
CREATE TABLE products
(
    id bigint auto_increment,
    title varchar(255) not null
    price decimal not null,
    CONSTRAINT products_pk PRIMARY KEY (id)
);
