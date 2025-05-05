CREATE TABLE IF NOT EXISTS products (
    id bigint auto_increment,
    title varchar(255) NOT NULL,
    price decimal NOT NULL,
    CONSTRAINT products_pk PRIMARY KEY (id)
);

--rollback DROP TABLE products;

