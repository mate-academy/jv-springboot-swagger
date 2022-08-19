package mate.academy.springboot.swagger.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class SortingPageUtil {
    private static final String SPLIT_ONE = ";";
    private static final String SPLIT_TWO = ":";
    private static final int DIRECTION = 1;
    private static final int FIELD = 1;

    public static Sort getSort(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(SPLIT_TWO)) {
            String[] sortingFields = sortBy.split(SPLIT_ONE);
            for (String field : sortingFields) {
                Sort.Order order;
                if (field.contains(SPLIT_TWO)) {
                    String[] fieldsAndDirections = field.split(SPLIT_TWO);
                    order = new Sort.Order(Sort.Direction.valueOf(fieldsAndDirections[DIRECTION]),
                            fieldsAndDirections[FIELD]);
                } else {
                    order = new Sort.Order(Sort.Direction.ASC, field);
                }
                orders.add(order);
            }
        } else {
            Sort.Order order = new Sort.Order(Sort.Direction.ASC, sortBy);
            orders.add(order);
        }
        return Sort.by(orders);
    }
}
