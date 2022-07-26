package mate.academy.springboot.swagger.service.sorting.impl;

import java.util.ArrayList;
import java.util.List;
import mate.academy.springboot.swagger.service.sorting.CustomSortingOrderBy;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CustomSortingOrderByImpl implements CustomSortingOrderBy {
    private static final Integer FIELD_INDEX = 0;
    private static final Integer SORTING_INDEX = 1;

    @Override
    public Sort sortBy(String sort) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sort.contains(":")) {
            String[] sortingFields = sort.split(";");
            for (String field : sortingFields) {
                Sort.Order order;
                if (field.contains(":")) {
                    String[] fieldsAndDirection = field.split(":");
                    order = new Sort.Order(Sort.Direction
                            .valueOf(fieldsAndDirection[SORTING_INDEX]),
                            fieldsAndDirection[FIELD_INDEX]);
                } else {
                    order = new Sort.Order(Sort.Direction.DESC, field);
                }
                orders.add(order);
            }
        } else {
            Sort.Order order = new Sort.Order(Sort.Direction.DESC, sort);
            orders.add(order);
        }
        return Sort.by(orders);
    }
}
