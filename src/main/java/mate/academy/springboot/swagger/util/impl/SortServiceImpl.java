package mate.academy.springboot.swagger.util.impl;

import java.util.ArrayList;
import java.util.List;
import mate.academy.springboot.swagger.util.SortService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class SortServiceImpl implements SortService {
    private static final String SEMICOLON = ";";
    private static final String COLON = ":";
    private static final int PLACE_OF_ARRAY_FIELD = 0;
    private static final int PLACE_OF_ARRAY_DIRECTION = 1;

    @Override
    public List<Sort.Order> sort(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(COLON)) {
            String[] sortingFields = sortBy.split(SEMICOLON);
            for (String field : sortingFields) {
                Sort.Order order;
                if (field.contains(COLON)) {
                    String[] fieldsAndDirection = field.split(COLON);
                    order = new Sort.Order(Sort.Direction
                            .valueOf(fieldsAndDirection[PLACE_OF_ARRAY_DIRECTION]),
                            fieldsAndDirection[PLACE_OF_ARRAY_FIELD]);
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
