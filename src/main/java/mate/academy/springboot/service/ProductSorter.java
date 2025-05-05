package mate.academy.springboot.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class ProductSorter {
    private static final int DIRECTION_INDEX = 1;
    private static final int FIELD_INDEX = 0;
    private static final String FIELDS_SEPARATOR = ";";
    private static final String FIELD_AND_DIRECTION_SEPARATOR = ":";

    public Sort sortByParam(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(FIELD_AND_DIRECTION_SEPARATOR)) {
            String[] sortingFields = sortBy.split(FIELDS_SEPARATOR);
            for (String sortingField : sortingFields) {
                Sort.Order order;
                if (sortingField.contains(FIELD_AND_DIRECTION_SEPARATOR)) {
                    String[] fieldsAndDirections = sortingField
                            .split(FIELD_AND_DIRECTION_SEPARATOR);
                    order = new Sort.Order(Sort.Direction
                            .valueOf(fieldsAndDirections[DIRECTION_INDEX]),
                            fieldsAndDirections[FIELD_INDEX]);
                } else {
                    order = new Sort.Order(Sort.Direction.DESC, sortingField);
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
