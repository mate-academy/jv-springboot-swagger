package mate.academy.springboot.swagger.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class SortUtil {
    public static final String COLON = ":";
    public static final String SEMI_COLON = ":";
    public static final int INDEX_OF_DIRECTION = 1;
    public static final int INDEX_OF_FIELD = 0;

    public Sort getSort(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(COLON)) {
            String[] sortingFields = sortBy.split(SEMI_COLON);
            for (String field : sortingFields) {
                Sort.Order order;
                if (field.contains(COLON)) {
                    String[] fieldsAndDirection = field.split(COLON);
                    order = new Sort.Order(Sort.Direction.valueOf(
                            fieldsAndDirection[INDEX_OF_DIRECTION]),
                            fieldsAndDirection[INDEX_OF_FIELD]);
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
