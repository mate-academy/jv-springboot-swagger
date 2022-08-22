package mate.academy.springboot.swagger.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class SortUtilImpl implements SortUtil {
    private static final String FIELD_SEPARATOR = ";";
    private static final String DIRECTION_SEPARATOR = ":";
    private static final int FIELD_INDEX = 0;
    private static final int DIRECTION_INDEX = 1;

    @Override
    public Sort getSort(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(DIRECTION_SEPARATOR)) {
            String[] sortingFields = sortBy.split(FIELD_SEPARATOR);
            for (String field : sortingFields) {
                Sort.Order order;
                if (field.contains(DIRECTION_SEPARATOR)) {
                    String[] fieldsAndDirections = field.split(DIRECTION_SEPARATOR);
                    order = new Sort.Order(Sort.Direction
                            .valueOf(fieldsAndDirections[DIRECTION_INDEX]),
                            fieldsAndDirections[FIELD_INDEX]);
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
