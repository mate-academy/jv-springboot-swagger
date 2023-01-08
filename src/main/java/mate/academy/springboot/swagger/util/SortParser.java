package mate.academy.springboot.swagger.util;

import org.springframework.data.domain.Sort;
import java.util.ArrayList;
import java.util.List;

public class SortParser {
    // products?count=20&page=1&sortBy=price:ASC;title:ASC
    public static Sort parse(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(":")) {
            String[] sortedFields = sortBy.split(";");
            for (String field : sortedFields) {
                Sort.Order order;
                if (field.contains(":")) {
                    String[] fieldsAndDirections = field.split(":");
                    order = new Sort.Order(Sort.Direction.valueOf(fieldsAndDirections[1]),
                            fieldsAndDirections[0]);
                } else {
                    order = new Sort.Order(Sort.Direction.DESC, field);
                }
                orders.add(order);
            }
        } else {
            Sort.Order order = new Sort.Order(Sort.Direction.DESC, sortBy);
            orders.add(order);
        }
        return Sort.by(orders);
    }
}
