package mate.academy.springboot.swagger.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;

public class SortService {
    public static Sort getSort(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        String[] sortingFields = sortBy.split(";");
        for (String sortingField : sortingFields) {
            Sort.Order order;
            if (sortBy.contains(":")) {
                String[] fieldAndOrder = sortingField.split(":");
                order = new Sort.Order(Sort.Direction.fromString(fieldAndOrder[1]),
                        fieldAndOrder[0]);
            } else {
                order = Sort.Order.asc(sortBy);
            }
            orders.add(order);
        }
        return Sort.by(orders);
    }
}
