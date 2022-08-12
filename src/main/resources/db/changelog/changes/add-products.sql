--liquibase formatted sql
--changeset <oleksandrkraievskyi>:<create-products-table>

INSERT INTO products (title, price) VALUES ('iPhone 11', 800);
INSERT INTO products (title, price) VALUES ('iPhone 11', 700);
INSERT INTO products (title, price) VALUES ('iPhone 11', 900);
INSERT INTO products (title, price) VALUES ('iPhone 11', 760);
INSERT INTO products (title, price) VALUES ('iPhone 11', 560);
INSERT INTO products (title, price) VALUES ('iPhone 11', 830);
INSERT INTO products (title, price) VALUES ('iPhone 11', 810);
INSERT INTO products (title, price) VALUES ('iPhone 11', 499);
INSERT INTO products (title, price) VALUES ('iPhone 11', 590);
INSERT INTO products (title, price) VALUES ('iPhone 11', 758);
INSERT INTO products (title, price) VALUES ('iPhone 11', 687);
INSERT INTO products (title, price) VALUES ('iPhone 11', 876);
INSERT INTO products (title, price) VALUES ('iPhone 11', 798);
INSERT INTO products (title, price) VALUES ('iPhone 11 red', 837);
INSERT INTO products (title, price) VALUES ('iPhone 11 blue', 743);
INSERT INTO products (title, price) VALUES ('iPhone 11 black', 935);
INSERT INTO products (title, price) VALUES ('iPhone 11 silver', 932);
INSERT INTO products (title, price) VALUES ('iPhone 11 gold', 622);
INSERT INTO products (title, price) VALUES ('iPhone 11 pink', 523);
INSERT INTO products (title, price) VALUES ('iPhone 11 128 GB', 934);
INSERT INTO products (title, price) VALUES ('iPhone 11 64 GB', 642);
INSERT INTO products (title, price) VALUES ('iPhone X 32 GB', 867);
INSERT INTO products (title, price) VALUES ('iPhone X black 64 GB', 912);
INSERT INTO products (title, price) VALUES ('iPhone X blue 128 GB', 831);
INSERT INTO products (title, price) VALUES ('iPhone X', 879);
INSERT INTO products (title, price) VALUES ('iPhone X', 819);
INSERT INTO products (title, price) VALUES ('iPhone X', 839);
INSERT INTO products (title, price) VALUES ('iPhone X', 889);
INSERT INTO products (title, price) VALUES ('iPhone X', 859);
INSERT INTO products (title, price) VALUES ('iPhone X', 799);
INSERT INTO products (title, price) VALUES ('iPhone X', 769);
INSERT INTO products (title, price) VALUES ('iPhone X', 810);
INSERT INTO products (title, price) VALUES ('iPhone X', 850);
INSERT INTO products (title, price) VALUES ('iPhone X', 831);
INSERT INTO products (title, price) VALUES ('iPhone X black 64 GB', 800);
INSERT INTO products (title, price) VALUES ('iPhone X silver 128 GB', 831);
INSERT INTO products (title, price) VALUES ('iPhone X blue', 879);
INSERT INTO products (title, price) VALUES ('iPhone X white', 819);
INSERT INTO products (title, price) VALUES ('iPhone X 32 GB', 867);
INSERT INTO products (title, price) VALUES ('Samsung A10', 799);
INSERT INTO products (title, price) VALUES ('Samsung A10', 699);
INSERT INTO products (title, price) VALUES ('Samsung A10', 490);
INSERT INTO products (title, price) VALUES ('Samsung A10', 679);
INSERT INTO products (title, price) VALUES ('Samsung A10', 726);
INSERT INTO products (title, price) VALUES ('Samsung A10', 947);
INSERT INTO products (title, price) VALUES ('Samsung A10', 810);
INSERT INTO products (title, price) VALUES ('Samsung A10', 499);
INSERT INTO products (title, price) VALUES ('Samsung A10', 590);
INSERT INTO products (title, price) VALUES ('Samsung A10', 758);
INSERT INTO products (title, price) VALUES ('Samsung A10', 687);
INSERT INTO products (title, price) VALUES ('Samsung A10', 876);
INSERT INTO products (title, price) VALUES ('Samsung A10', 798);
INSERT INTO products (title, price) VALUES ('Samsung A10 red', 700);
INSERT INTO products (title, price) VALUES ('Samsung A10 yellow', 765);
INSERT INTO products (title, price) VALUES ('Samsung A10 white', 935);
INSERT INTO products (title, price) VALUES ('Samsung A10 silver', 932);
INSERT INTO products (title, price) VALUES ('Samsung A10 gold', 600);
INSERT INTO products (title, price) VALUES ('Samsung A10 pink', 500);
INSERT INTO products (title, price) VALUES ('Samsung A10 64 GB', 934);
INSERT INTO products (title, price) VALUES ('Samsung A10 64 GB', 600);

--rollback DELETE FROM products;

