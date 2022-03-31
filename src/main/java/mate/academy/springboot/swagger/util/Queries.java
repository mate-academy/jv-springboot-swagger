package mate.academy.springboot.swagger.util;

public class Queries {
    public static final String PRODUCT_QUERY_VALUE = "SELECT * FROM products "
            + "AS p WHERE p.price BETWEEN ?1 AND ?2";
    public static final String PRODUCT_QUERY_COUNT = "SELECT count(*) FROM products "
            + "AS p WHERE p.price BETWEEN ?1 AND ?2";
    public static final String GET_MAX_PRICE_QUERY = "SELECT max(products.price) "
            + "FROM products";
    public static final String GET_MIN_PRICE_QUERY = "SELECT min(products.price) "
            + "FROM products";

}
