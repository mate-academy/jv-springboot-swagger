package mate.academy.springboot.swagger.parser.impl;

import java.util.ArrayList;
import java.util.List;
import mate.academy.springboot.swagger.parser.Parser;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class SortQueryParser implements Parser<List<Sort.Order>, String> {
    private static final String FIELD_SEPARATOR = ";";
    private static final String FIELD_DIRECTION_SEPARATOR = ":";
    private static final int DIRECTION_INDEX = 1;
    private static final int FIELD_INDEX = 0;

    @Override
    public List<Sort.Order> parse(String query) {
        List<Sort.Order> orders = new ArrayList<>();
        if (query.contains(FIELD_SEPARATOR)) {
            String[] sortingFields = query.split(FIELD_SEPARATOR);
            for (String field : sortingFields) {
                orders.add(parseField(field));
            }
        } else {
            Sort.Order order = parseField(query);
            orders.add(order);
        }
        return orders;
    }

    private Sort.Order parseField(String field) {
        Sort.Order order;
        if (field.contains(FIELD_DIRECTION_SEPARATOR)) {
            String[] fieldsAndDirections = field.split(FIELD_DIRECTION_SEPARATOR);
            order = new Sort.Order(
                    Sort.Direction.valueOf(fieldsAndDirections[DIRECTION_INDEX]),
                    fieldsAndDirections[FIELD_INDEX]
            );
        } else {
            order = new Sort.Order(Sort.Direction.DESC, field);
        }
        return order;
    }
}
