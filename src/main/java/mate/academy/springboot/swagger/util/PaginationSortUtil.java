package mate.academy.springboot.swagger.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class PaginationSortUtil {
    private static final int DIRECTION_INDEX = 1;
    private static final int FIELD_INDEX = 0;
    private static final String COLON = ":";
    private static final String SEMICOLON = ";";

    public PageRequest getPageRequest(Integer count, Integer page, String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(SEMICOLON)) {
            String[] sortingFields = sortBy.split(SEMICOLON);
            for (String field: sortingFields) {
                setOrderByField(orders, field);
            }
        } else {
            setOrderByField(orders, sortBy);
        }
        Sort sort = Sort.by(orders);
        return PageRequest.of(page, count, sort);
    }

    private void setOrderByField(List<Sort.Order> orders, String field) {
        Sort.Order order;
        if (field.contains(COLON)) {
            String[] fieldsAndDirections = field.split(COLON);
            order = new Sort
                    .Order(Sort.Direction.valueOf(fieldsAndDirections[DIRECTION_INDEX]),
                    fieldsAndDirections[FIELD_INDEX]);
        } else {
            order = new Sort.Order(Sort.Direction.ASC, field);
        }
        orders.add(order);
    }
}
