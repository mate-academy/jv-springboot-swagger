package mate.academy.springboot.swagger.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class SortParser {

    private static final int DIRECTION_OF_SORTING = 1;
    private static final int FIELD_OF_SORTING = 0;
    private static final String COLON = ":";
    private static final String SEMICOLON = ";";

    public List<Sort.Order> getSortParams(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(":")) {
            String[] sortingFields = sortBy.split(SEMICOLON);
            for (String field : sortingFields) {
                Sort.Order order;
                if (field.contains(":")) {
                    String[] fieldsAndDirections = field.split(COLON);
                    order =
                            new Sort.Order(Sort.Direction
                                    .valueOf(fieldsAndDirections[DIRECTION_OF_SORTING]),
                            fieldsAndDirections[FIELD_OF_SORTING]);
                } else {
                    order = new Sort.Order(Sort.Direction.DESC, field);
                }
                orders.add(order);
            }
        } else {
            Sort.Order order = new Sort.Order(Sort.Direction.DESC, sortBy);
            orders.add(order);
        }
        return orders;
    }
}
