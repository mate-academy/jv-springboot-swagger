package mate.academy.springboot.swagger.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class Parser {
    private static final String SEMICOLON = ";";
    private static final String COLON = ":";
    private static final int FIELD_VALUE_INDEX = 0;
    private static final int DIR_VALUE_INDEX = 1;

    public Sort parse(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(COLON)) {
            String[] sortingFields = sortBy.split(SEMICOLON);
            for (String field : sortingFields) {
                Sort.Order order;
                if (field.contains(COLON)) {
                    String[] fieldsAndDirs = field.split(COLON);
                    order = new Sort.Order(Sort.Direction.valueOf(fieldsAndDirs[DIR_VALUE_INDEX]),
                            fieldsAndDirs[FIELD_VALUE_INDEX]);
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
