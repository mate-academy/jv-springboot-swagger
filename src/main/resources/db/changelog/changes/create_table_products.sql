
CREATE TABLE IF NOT EXISTS products (
                          id BIGINT,
                          title VARCHAR(50) NOT NULL,
                          price DECIMAL(6,2) NOT NULL,
                          CONSTRAINT products_pk PRIMARY KEY (`id`)
);