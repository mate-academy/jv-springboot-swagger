package mate.academy.springboot.swagger.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class SortUtil {
    private static final String COLON = ":";
    private static final String SEMI_COLON = ";";
    private static final int DIRECTION_INDEX = 1;
    private static final int FIELD_INDEX = 0;

    public Sort sort(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(COLON)) {
            String[] sortingFields = sortBy.split(SEMI_COLON);
            for (String field : sortingFields) {
                Sort.Order order;
                if (field.contains(COLON)) {
                    String[] fieldsAndDirections = field.split(COLON);
                    order = new Sort.Order(
                            Sort.Direction.valueOf(fieldsAndDirections[DIRECTION_INDEX]),
                            fieldsAndDirections[FIELD_INDEX]);
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
