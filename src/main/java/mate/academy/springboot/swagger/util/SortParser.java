package mate.academy.springboot.swagger.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class SortParser {
    private static final int FIELD_INDEX = 1;
    private static final int DIRECTION_INDEX = 0;
    private static final String SEMICOLON = ";";
    private static final String COLON = ":";

    public Sort parse(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(COLON)) {
            String[] sortedFields = sortBy.split(SEMICOLON);
            for (String field : sortedFields) {
                Sort.Order order;
                if (field.contains(COLON)) {
                    String[] fieldsAndDirections = field.split(COLON);
                    order = new Sort.Order(Sort.Direction.valueOf(fieldsAndDirections[FIELD_INDEX]),
                            fieldsAndDirections[DIRECTION_INDEX]);
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
