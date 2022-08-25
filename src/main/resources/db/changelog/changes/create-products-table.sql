CREATE TABLE IF NOT EXISTS products
(
    id BIGINT AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    price DECIMAL NOT NULL,
    CONSTRAINT products_pk PRIMARY KEY (id)
);

--rollback DROP TABLE products;
