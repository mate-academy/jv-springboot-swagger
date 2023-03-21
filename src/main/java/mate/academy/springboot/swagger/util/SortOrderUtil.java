package mate.academy.springboot.swagger.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class SortOrderUtil {
    private static final int ORDER_DIRECTIONAL_INDEX = 1;
    private static final int FIELD_INDEX = 0;
    private static Sort.Order order;

    public Sort sort(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        String[] sortingFields = sortBy.split(";");
        for (String field : sortingFields) {
            if (field.contains(":")) {
                String[] fieldsAndDirections = field.split(":");
                order = new Sort.Order(
                        Sort.Direction.valueOf(fieldsAndDirections[ORDER_DIRECTIONAL_INDEX]),
                        fieldsAndDirections[FIELD_INDEX]);
            } else {
                order = new Sort.Order(Sort.Direction.DESC, field);
            }
            orders.add(order);
        }return Sort.by(orders);
    }
}
