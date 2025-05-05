package mate.academy.springboot.swagger.service.impl;

import java.util.ArrayList;
import java.util.List;
import mate.academy.springboot.swagger.service.SortingStrategy;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class SortingStrategyImpl implements SortingStrategy {
    private static final String FIELD_AND_SORTING_TYPE_SEPARATION = ":";
    private static final String FIELD_SEPARATION = ";";
    private static final int TYPE_INDEX = 1;
    private static final int FIELD_INDEX = 0;

    @Override
    public List<Sort.Order> getSortingOrders(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(FIELD_AND_SORTING_TYPE_SEPARATION)) {
            String[] sortingFields = sortBy.split(FIELD_SEPARATION);
            for (String field : sortingFields) {
                Sort.Order order;
                if (field.contains(FIELD_AND_SORTING_TYPE_SEPARATION)) {
                    String[] fieldsAndDirections = field.split(FIELD_AND_SORTING_TYPE_SEPARATION);
                    order = new Sort.Order(Sort.Direction.valueOf(fieldsAndDirections[TYPE_INDEX]),
                            fieldsAndDirections[FIELD_INDEX]);
                } else {
                    order = new Sort.Order(Sort.Direction.ASC, field);
                }
                orders.add(order);
            }
        } else {
            Sort.Order order = new Sort.Order(Sort.Direction.ASC, sortBy);
            orders.add(order);
        }
        return orders;
    }
}
