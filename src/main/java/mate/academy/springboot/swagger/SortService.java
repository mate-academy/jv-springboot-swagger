package mate.academy.springboot.swagger;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class SortService {
    private static final String COLON_REGEX = ":";
    private static final String SEMICOLON_REGEX = ";";
    private static final int DIRECTION_INDEX = 1;
    private static final int FIELD_INDEX = 0;

    public List<Sort.Order> getSorting(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(COLON_REGEX)) {
            String[] sortingFields = sortBy.split(SEMICOLON_REGEX);
            for (String field : sortingFields) {
                Sort.Order order;
                if (field.contains(":")) {
                    String[] fieldsAndDirections = field.split(COLON_REGEX);
                    order = new Sort.Order(Sort.Direction
                            .valueOf(fieldsAndDirections[DIRECTION_INDEX]),
                            fieldsAndDirections[FIELD_INDEX]);
                } else {
                    order = new Sort.Order(Sort.Direction.DESC, sortBy);
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
