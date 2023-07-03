package mate.academy.springboot.swagger.service.impl;

import java.util.ArrayList;
import java.util.List;
import mate.academy.springboot.swagger.service.SortService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class SortServiceImpl implements SortService {
    private static final String FIELDS_SEPARATOR = ";";
    private static final String FIELD_AND_DIRECTION_SEPARATOR = ":";
    private static final int FIELD_INDEX = 0;
    private static final int DIRECTION_INDEX = 1;

    @Override
    public Sort sort(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(":")) {
            String[] sortingFields = sortBy.split(FIELDS_SEPARATOR);
            for (String field : sortingFields) {
                Sort.Order order;
                if (field.contains(FIELD_AND_DIRECTION_SEPARATOR)) {
                    String[] fieldsAndDirections = field.split(FIELD_AND_DIRECTION_SEPARATOR);
                    order = new Sort.Order(Sort.Direction
                            .valueOf(fieldsAndDirections[DIRECTION_INDEX]),
                            fieldsAndDirections[FIELD_INDEX]);
                } else {
                    order = new Sort.Order(Sort.Direction.DESC,field);
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
