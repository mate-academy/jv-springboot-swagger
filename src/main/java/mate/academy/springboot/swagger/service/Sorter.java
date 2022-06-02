package mate.academy.springboot.swagger.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class Sorter {
    private static final String PARAMETER_SPLITTER = ";";
    private static final String FIELD_AND_DIRECTION_SPLITTER = ":";
    private static final Integer FIELD_INDEX = 0;
    private static final Integer DIRECTION_INDEX = 1;

    public Sort getSortParams(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        // IDK why we need this part...        if (sortBy.contains(":")) {
        String[] sortingFields = sortBy.split(PARAMETER_SPLITTER);
        for (String field : sortingFields) {
            Sort.Order order;
            if (field.contains(":")) {
                String[] fieldsAndDirections = field.split(FIELD_AND_DIRECTION_SPLITTER);
                order = new Sort.Order(Sort.Direction.valueOf(fieldsAndDirections[DIRECTION_INDEX]),
                        fieldsAndDirections[FIELD_INDEX]);
            } else {
                order = new Sort.Order(Sort.Direction.ASC, field);
            }
            orders.add(order);
        }
        /*
        ... and this        } else {
            Sort.Order order = new Sort.Order(Sort.Direction.DESC, sortBy);
            orders.add(order);
            because request like "http://localhost:8080/products?sortBy=id;price" (without specifying the sort order) won't work
        }
        */
        return Sort.by(orders);
    }
}
