package mate.academy.springboot.swagger.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;

public class SortUtil {
    private static final String COLON = ":";
    private static final String SEMICOLON = ";";
    private static final int FIRST_INDEX = 1;
    private static final int ZERO_INDEX = 1;

    public static Sort sort(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(COLON)) {
            String[] sortingFields = sortBy.split(SEMICOLON);
            for (String field: sortingFields) {
                Sort.Order order;
                if (field.contains(COLON)) {
                    String[] fieldsAndDirections = field.split(COLON);
                    order = new Sort.Order(Sort.Direction.valueOf(fieldsAndDirections[FIRST_INDEX]),
                            fieldsAndDirections[ZERO_INDEX]);
                } else {
                    order = new Sort.Order(Sort.Direction.DESC, field);
                }
                orders.add(order);
            }
        }
        return Sort.by(orders);
    }
}
