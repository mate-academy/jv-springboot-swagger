package mate.academy.springboot.swagger.service.impl;

import java.util.ArrayList;
import java.util.List;
import mate.academy.springboot.swagger.service.SortingService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class SortingServiceImpl implements SortingService {
    private static final String DELIMITER = ":";
    private static final String SEPARATOR = ";";

    @Override
    public List<Sort.Order> parseSortOrders(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(DELIMITER)) {
            String[] sortingFields = sortBy.split(SEPARATOR);
            for (String field : sortingFields) {
                orders.add(getSortOrder(field));
            }
        } else {
            Sort.Order order = new Sort.Order(Sort.Direction.DESC, sortBy);
            orders.add(order);
        }
        return orders;
    }

    private Sort.Order getSortOrder(String field) {
        Sort.Order order;
        if (field.contains(DELIMITER)) {
            String[] fieldsAndDirections = field.split(DELIMITER);
            order = new Sort.Order(Sort.Direction.valueOf(fieldsAndDirections[1]),
                    fieldsAndDirections[0]);
        } else {
            order = new Sort.Order(Sort.Direction.DESC, field);
        }
        return order;
    }
}
