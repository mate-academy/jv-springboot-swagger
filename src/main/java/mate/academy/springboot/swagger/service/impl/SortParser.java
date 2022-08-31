package mate.academy.springboot.swagger.service.impl;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class SortParser {
    private static final int ORDER_FIELD = 0;
    private static final int ORDER_TYPE = 1;
    private static final String SEPARATOR = ":'";

    public Sort.Order parseSortCondition(String sort) {
        Sort.Order order;
        if (sort.contains(SEPARATOR)) {
            String[] parts = sort.split(SEPARATOR);
            order = Sort.Order.by(parts[ORDER_FIELD]);
            if (parts[ORDER_TYPE].equalsIgnoreCase("DESC")) {
                order = order.with(Sort.Direction.DESC);
            }
        } else {
            order = Sort.Order.by(sort);
        }
        return order;
    }
}
