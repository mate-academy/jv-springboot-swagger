package mate.academy.springboot.swagger.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class PageRequestUtil {
    private static final String SEPARATOR_ATTRIBUTES = ":";
    private static final String SEPARATOR_PARAMS = ";";
    private static final int INDEX_FIELD_NAME = 0;
    private static final int INDEX_DIRECTION_VALUE = 1;
    public static PageRequest getPageRequest(Integer page, Integer count, String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(SEPARATOR_ATTRIBUTES)) {
            String[] sortingField = sortBy.split(SEPARATOR_PARAMS);
            for (String field : sortingField) {
                Sort.Order order;
                if (field.contains(SEPARATOR_ATTRIBUTES)) {
                    String[] fieldsAndDirection = field.split(SEPARATOR_ATTRIBUTES);
                    order = new Sort.Order(Sort.Direction.valueOf(fieldsAndDirection[INDEX_DIRECTION_VALUE]),
                            fieldsAndDirection[INDEX_FIELD_NAME]);
                } else {
                    order = new Sort.Order(Sort.Direction.DESC, field);
                }
                orders.add(order);
            }
        } else {
            Sort.Order order = new Sort.Order(Sort.Direction.DESC, sortBy);
            orders.add(order);
        }
        Sort sort = Sort.by(orders);
        return PageRequest.of(page, count, sort);
    }
}
