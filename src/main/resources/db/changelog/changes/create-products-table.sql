--liquibase formatted sql
--changeset <vo-v4>:<create-products-table>
CREATE TABLE IF NOT EXISTS products
(
    id bigint NOT NULL AUTO_INCREMENT,
    title varchar(255) NOT NULL,
    price decimal(10,0) NOT NULL,
    CONSTRAINT products_pk PRIMARY KEY (id)
    )
--rollback DROP TABLE products;