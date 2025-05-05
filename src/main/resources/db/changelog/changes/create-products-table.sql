CREATE TABLE IF NOT EXISTS products
(
    id bigint AUTO_INCREMENT NOT NULL,
    title varchar(255) NOT NULL,
    price decimal NOT NULL,
    CONSTRAINT products_pk PRIMARY KEY (id)
);

