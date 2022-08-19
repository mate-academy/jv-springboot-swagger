package mate.academy.springboot.swagger.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;

public class SortProductUtil {
    private static final String PARAMETER_SPLITERATOR = ";";
    private static final String ORDER_SPLITERATOR = ":";

    public static Sort getSortingProduct(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(":")) {
            String[] sortingFields = sortBy.split(PARAMETER_SPLITERATOR);
            for (String field : sortingFields) {
                Sort.Order order;
                if (field.contains(":")) {
                    String[] fieldAndDirections = field.split(ORDER_SPLITERATOR);
                    order = new Sort.Order(Sort.Direction.valueOf(fieldAndDirections[1]), fieldAndDirections[0]);
                } else {
                    order = new Sort.Order(Sort.Direction.ASC, field);
                }
                orders.add(order);
            }
        } else {
            Sort.Order order = new Sort.Order(Sort.Direction.ASC, sortBy);
            orders.add(order);
        }
        return Sort.by(orders);
    }
}
