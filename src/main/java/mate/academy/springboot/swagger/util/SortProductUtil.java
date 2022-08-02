package mate.academy.springboot.swagger.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class SortProductUtil {
    private static final int FIELD = 0;
    private static final int DIRECTION = 1;

    public List<Sort.Order> getSortedProduct(String sortParameter) {
        //localhost:8080/products?size=20&page1&sortBy=price:ASC;title:ASC
        List<Sort.Order> orders = new ArrayList<>();
        if (sortParameter.contains(":")) {
            String[] sortingFields = sortParameter.split(";");
            for (String field : sortingFields) {
                Sort.Order order;
                if (field.contains(":")) {
                    String[] fieldAndDirections = field.split(":");
                    order = new Sort.Order(Sort.Direction.valueOf(fieldAndDirections[DIRECTION]),
                            fieldAndDirections[FIELD]);
                } else {
                    order = new Sort.Order(Sort.Direction.DESC, field);
                }
                orders.add(order);
            }
        } else {
            Sort.Order order = new Sort.Order(Sort.Direction.DESC, sortParameter);
            orders.add(order);
        }
        return orders;
    }
}
