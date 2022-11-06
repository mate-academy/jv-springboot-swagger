package mate.academy.springboot.swagger.util.impl;

import java.util.ArrayList;
import java.util.List;
import mate.academy.springboot.swagger.util.SortByParser;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class SortByParserImpl implements SortByParser {
    private static final int DIRECTION_INDEX = 1;
    private static final int FIELD_NAME_INDEX = 0;
    private static final Sort.Direction DEFAULT_DIRECTION = Sort.Direction.ASC;

    @Override
    public Sort parse(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(";")) {
            String[] fieldsAndDirections = sortBy.split(";");
            for (String fieldAndDirection : fieldsAndDirections) {
                orders.add(getOrder(fieldAndDirection));
            }
        } else {
            orders.add(getOrder(sortBy));
        }
        return Sort.by(orders);
    }

    private Sort.Order getOrder(String fieldAndDirection) {
        Sort.Order order;
        if (fieldAndDirection.contains(":")) {
            String[] split = fieldAndDirection.split(":");
            order = new Sort.Order(Sort.Direction.valueOf(split[DIRECTION_INDEX]),
                    split[FIELD_NAME_INDEX]);
        } else {
            order = new Sort.Order(DEFAULT_DIRECTION, fieldAndDirection);
        }
        return order;
    }
}
