package mate.academy.springboot.swagger.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class PageRequestPrepare {
    private static final String FIRST_SEPARATOR = ";";
    private static final String SECOND_SEPARATOR = ":";
    private static final Sort.Direction DEFAULT_SORT_DIRECTION = Sort.Direction.DESC;

    public static PageRequest getPageRequestObj(Integer size, Integer page, String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(SECOND_SEPARATOR)) {
            String[] sortingFields = sortBy.split(FIRST_SEPARATOR);
            for (String sortingField : sortingFields) {
                Sort.Order order;
                if (sortingField.contains(":")) {
                    String[] fieldsAndDirections = sortingField.split(SECOND_SEPARATOR);
                    order = new Sort.Order(Sort.Direction.valueOf(fieldsAndDirections[1]),
                            fieldsAndDirections[0]);
                } else {
                    order = new Sort.Order(DEFAULT_SORT_DIRECTION, sortingField);
                }
                orders.add(order);
            }
        } else {
            Sort.Order order = new Sort.Order(DEFAULT_SORT_DIRECTION, sortBy);
            orders.add(order);
        }

        Sort sort = Sort.by(orders);
        return PageRequest.of(page, size, sort);
    }
}
