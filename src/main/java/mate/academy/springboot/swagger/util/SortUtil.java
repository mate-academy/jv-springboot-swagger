package mate.academy.springboot.swagger.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;

@Component
public class SortUtil {
    private static final int DIRECTION_INDEX = 1;
    private static final int PARAMETER_INDEX = 0;

    public List<Sort.Order> createSortOrders(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(":")) {
            String[] sortingFields = sortBy.split(";");
            for (String field : sortingFields) {
                Sort.Order order;
                if (field.contains(":")) {
                    String[] fieldsAndDirections = field.split(":");
                    order = new Sort.Order(Sort.Direction.valueOf(
                            fieldsAndDirections[DIRECTION_INDEX]),
                            fieldsAndDirections[PARAMETER_INDEX]);
                } else {
                    order = new Sort.Order(Direction.ASC, field);
                }
                orders.add(order);
            }
        } else {
            Sort.Order order = new Sort.Order(Direction.ASC, sortBy);
            orders.add(order);
        }
        return orders;
    }

    public Sort createSort(List<Sort.Order> orders) {
        return Sort.by(orders);
    }
}
