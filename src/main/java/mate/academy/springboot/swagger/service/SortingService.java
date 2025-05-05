package mate.academy.springboot.swagger.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;

public class SortingService {
    public static List<Sort.Order> parseSortingOrders(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(":")) {
            String[] sortingFields = sortBy.split(";");
            for (String field : sortingFields) {
                orders.add(getSortOrder(field));
            }
        } else {
            Sort.Order order = new Sort.Order(Sort.Direction.DESC, sortBy);
            orders.add(order);
        }
        return orders;
    }

    private static Sort.Order getSortOrder(String field) {
        Sort.Order order;
        if (field.contains(":")) {
            String[] fieldDirections = field.split(":");
            order = new Sort.Order(Sort.Direction.valueOf(fieldDirections[1]), fieldDirections[0]);
        } else {
            order = new Sort.Order(Sort.Direction.ASC, field);
        }
        return order;
    }
}
