package mate.academy.springboot.swagger.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class SortOrderUtil {
    private static final int ORDER_DIRECTIONAL_INDEX = 1;
    private static final int FIELD_INDEX = 0;
    private static final String FIELD_SEPARATOR = ";";
    private static final String DIRECTIONAL_AND_FIELD_SEPARATOR = ":";

    public Sort sort(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        Sort.Order order;
        String[] sortingFields = sortBy.split(FIELD_SEPARATOR);
        for (String field : sortingFields) {
            if (field.contains(DIRECTIONAL_AND_FIELD_SEPARATOR)) {
                String[] fieldsAndDirections = field.split(DIRECTIONAL_AND_FIELD_SEPARATOR);
                order = new Sort.Order(
                        Sort.Direction.valueOf(fieldsAndDirections[ORDER_DIRECTIONAL_INDEX]),
                        fieldsAndDirections[FIELD_INDEX]);
            } else {
                order = new Sort.Order(Sort.Direction.DESC, field);
            }
            orders.add(order);
        }
        return Sort.by(orders);
    }
}
