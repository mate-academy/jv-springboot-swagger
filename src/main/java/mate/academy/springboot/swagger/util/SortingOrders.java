package mate.academy.springboot.swagger.util;

import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class SortingOrders {
    public static List<Sort.Order> getOrders(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(":")) {
            String[] sortingFields = sortBy.split(";");
            for (String field : sortingFields) {
                Sort.Order order;
                if (field.contains(":")) {
                    String[] filedAndDirections = field.split(":");
                    order = new Sort.Order(Sort.Direction.valueOf(filedAndDirections[1]),
                            filedAndDirections[0]);
                } else {
                    order = new Sort.Order(Sort.Direction.DESC, field);
                }
                orders.add(order);
            }
        } else {
            Sort.Order order = new Sort.Order(Sort.Direction.DESC, sortBy);
            orders.add(order);
        }
        return orders;
    }
}
