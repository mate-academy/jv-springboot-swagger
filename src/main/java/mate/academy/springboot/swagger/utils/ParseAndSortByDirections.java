package mate.academy.springboot.swagger.utils;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;

public class ParseAndSortByDirections {

    public static Sort sortOrders(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        Sort.Order order;
        if (sortBy.contains(":")) {
            String[] sortingFields = sortBy.split(";");
            for (String field : sortingFields) {
                if (field.contains(":")) {
                    order = buildSort(field);
                    orders.add(order);
                }
            }
        } else {
            order = new Sort.Order(Sort.Direction.ASC, sortBy);
            orders.add(order);
        }
        return Sort.by(orders);
    }

    private static Sort.Order buildSort(String keyAndDirection) {
        String[] split = keyAndDirection.split(":");
        String key = split[0];
        String direction = split[1];
        switch (direction.toUpperCase()) {
            case "ASC":
                return Sort.Order.asc(key);

            case "DESC":
                return Sort.Order.desc(key);

            default: throw new
                    IllegalArgumentException("Sorting direction must to be only ASC or DESC");
        }
    }
}
