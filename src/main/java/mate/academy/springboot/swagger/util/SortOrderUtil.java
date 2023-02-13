package mate.academy.springboot.swagger.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;

public class SortOrderUtil {
    private SortOrderUtil() {
    }

    public static Sort getSorter(String[] sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        for (String field : sortBy) {
            if (field.contains(":")) {
                String[] fieldAndOrder = field.split(":");
                Sort.Order order = new Sort.Order(Sort.Direction.valueOf(fieldAndOrder[1]),
                        fieldAndOrder[0]);
                orders.add(order);
            } else {
                Sort.Order order = Sort.Order.asc(field);
                orders.add(order);
            }
        }
        return Sort.by(orders);
    }
}
