package mate.academy.springboot.swagger.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class PaginationUtils {
    private static final String SEPARATOR_ATTRIBUTES = ":";
    private static final String SEPARATOR_PARAMS = ";";
    private static final int INDEX_OF_FIELD_NAME = 0;
    private static final int INDEX_OF_DIRECTION_VALUE = 1;

    public static PageRequest parse(Integer page, Integer count, String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(":")) {
            String[] sortingFields = sortBy.split(SEPARATOR_PARAMS);
            for (String field : sortingFields) {
                Sort.Order order;
                if (field.contains(":")) {
                    String[] fieldsAndDirections = field.split(SEPARATOR_ATTRIBUTES);
                    order = new Sort.Order(Sort.Direction.valueOf(
                            fieldsAndDirections[INDEX_OF_DIRECTION_VALUE]),
                            fieldsAndDirections[INDEX_OF_FIELD_NAME]);
                } else {
                    order = new Sort.Order(Sort.Direction.DESC, field);
                }
                orders.add(order);
            }
        }
        Sort sort = Sort.by(sortBy);
        return PageRequest.of(page, count, sort);
    }
}
