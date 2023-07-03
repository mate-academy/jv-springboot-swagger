package mate.academy.springboot.swagger.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class SortParser {
    private static final int DIRECTION = 1;
    private static final int FIELD = 0;
    private static final String FIELD_REGEX = ";";
    private static final String FIELD_AND_DIRECTION_REGEX = ":";

    public Sort sortBy(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(FIELD_AND_DIRECTION_REGEX)) {
            String[] sortingFields = sortBy.split(FIELD_REGEX);
            for (String field : sortingFields) {
                Sort.Order order;
                if (field.contains(FIELD_AND_DIRECTION_REGEX)) {
                    String[] fieldAndDirections = field.split(FIELD_AND_DIRECTION_REGEX);
                    order = new Sort.Order(Sort.Direction.valueOf(fieldAndDirections[DIRECTION]),
                            fieldAndDirections[FIELD]);
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
