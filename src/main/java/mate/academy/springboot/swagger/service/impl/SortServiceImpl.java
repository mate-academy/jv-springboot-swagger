package mate.academy.springboot.swagger.service.impl;

import java.util.ArrayList;
import java.util.List;
import mate.academy.springboot.swagger.service.SortService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class SortServiceImpl implements SortService {
    public static final Integer FIELD_INDEX = 0;
    public static final Integer DIRECTION_INDEX = 1;

    @Override
    public List<Sort.Order> sort(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(":")) {
            String[] sortingFields = sortBy.split(";");
            for (String field : sortingFields) {
                Sort.Order order;
                if (field.contains(":")) {
                    String[] fieldsAndDirections = field.split(":");
                    order = new Sort.Order(Sort.Direction.valueOf(
                            fieldsAndDirections[DIRECTION_INDEX]),
                            fieldsAndDirections[FIELD_INDEX]);
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
