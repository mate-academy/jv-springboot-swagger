package mate.academy.springboot.swagger.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;

public class SortingUtil {
    private static final String COLON = ":";
    private static final String SEMI_COLON = ";";
    private static final int FIRST_ELEMENT = 0;
    private static final int SECOND_ELEMENT = 1;

    public static Sort sortBy(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(COLON)) {
            String[] sortingFields = sortBy.split(SEMI_COLON);
            for (String field : sortingFields) {
                Sort.Order order;
                if (field.contains(SEMI_COLON)) {
                    String[] filedAndDirections = field.split(COLON);
                    order = new Sort.Order(
                            Sort.Direction.valueOf(filedAndDirections[SECOND_ELEMENT]),
                            filedAndDirections[FIRST_ELEMENT]);
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
