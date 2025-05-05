--liquibase formatted sql
--changeset <msemisoshenko>:<create-products-table>
CREATE TABLE IF NOT EXISTS `products` (
                                          `id` BIGINT AUTO_INCREMENT,
                                          `title` VARCHAR(255) NOT NULL,
    `price` DECIMAL NOT NULL,
    CONSTRAINT products_sk PRIMARY KEY (`id`)
    );

--rollback DROP TABLE products;
