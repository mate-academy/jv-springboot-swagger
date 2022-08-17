CREATE TABLE IF NOT EXISTS products
(
    id serial PRIMARY KEY NOT NULL,
    title VARCHAR(255) NOT NULL,
    price numeric(19, 2) NOT NULL
);
