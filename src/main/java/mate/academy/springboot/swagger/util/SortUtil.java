package mate.academy.springboot.swagger.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class SortUtil {
    private static final String SORT_DELIMITER = ":";
    private static final String PARAM_DELIMITER = ";";

    public Sort sortData(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(SORT_DELIMITER)) {
            String[] sortingFields = sortBy.split(PARAM_DELIMITER);
            for (String field: sortingFields) {
                Sort.Order order;
                if (field.contains(SORT_DELIMITER)) {
                    String[] fieldsAndDirections = field.split(SORT_DELIMITER);
                    String sortingDirection = fieldsAndDirections[1];
                    String sortingField = fieldsAndDirections[0];
                    order = new Sort.Order(
                            Sort.Direction.valueOf(sortingDirection),
                            sortingField);
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
