package mate.academy.springboot.swagger.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;

public class SortUtil {
    private static final String COLON = ":";
    private static final String SEMICOLON = ";";
    private static final int FIRST_INDEX = 0;
    private static final int SECOND_INDEX = 1;

    public List<Sort.Order> sortOrders(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(COLON)) {
            String[] sortingFields = sortBy.split(SEMICOLON);
            for (String field : sortingFields) {
                Sort.Order order;
                if (field.contains(COLON)) {
                    String[] fieldAndDirection = field.split(COLON);
                    order = new Sort.Order(Sort.Direction.valueOf(fieldAndDirection[SECOND_INDEX]),
                            fieldAndDirection[FIRST_INDEX]);
                } else {
                    order = new Sort.Order(Sort.Direction.ASC, field);
                }
                orders.add(order);
            }
        } else {
            Sort.Order order = new Sort.Order(Sort.Direction.ASC, sortBy);
            orders.add(order);
        }
        return orders;
    }
}
