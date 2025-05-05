package mate.academy.springboot.swagger.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class SortUtil {
    private static final String PARAMS_SEPARATOR = ";";
    private static final String FIELDS_AND_DIRECTION_SEPARATOR = ":";

    public static List<Sort.Order> parse(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(FIELDS_AND_DIRECTION_SEPARATOR)) {
            String[] sortingFields = sortBy.split(PARAMS_SEPARATOR);
            for (String field : sortingFields) {
                Sort.Order order;
                if (field.contains(FIELDS_AND_DIRECTION_SEPARATOR)) {
                    String[] fieldAndDirection = field.split(FIELDS_AND_DIRECTION_SEPARATOR);
                    order = new Sort.Order(Sort.Direction.valueOf(fieldAndDirection[1]),
                            fieldAndDirection[0]);
                } else {
                    order = new Sort.Order(Sort.Direction.DESC, field);
                }
                orders.add(order);
            }
        } else {
            orders.add(new Sort.Order(Sort.Direction.DESC, sortBy));
        }
        return orders;
    }
}
