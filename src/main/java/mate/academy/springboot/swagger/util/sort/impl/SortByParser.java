package mate.academy.springboot.swagger.util.sort.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.util.sort.SortParser;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class SortByParser implements SortParser {
    private static final String BY_SPLITERATOR = ":";
    private static final String AND_SPLITERATOR = ";";
    private static final Sort.Direction DEFAULT_DIRECTION = Sort.Direction.ASC;

    @Override
    public Sort parse(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        List<String> sortingFields = sortBy.contains(AND_SPLITERATOR)
                ? Arrays.stream(sortBy.split(AND_SPLITERATOR))
                .collect(Collectors.toList())
                : List.of(sortBy);
        for (String field : sortingFields) {
            Sort.Order order;
            if (field.contains(BY_SPLITERATOR)) {
                String[] fieldAndDirection = field.split(BY_SPLITERATOR);
                order = new Sort.Order(Sort.Direction.valueOf(fieldAndDirection[1]),
                        fieldAndDirection[0]);
            } else {
                order = new Sort.Order(DEFAULT_DIRECTION, field);
            }
            orders.add(order);
        }
        return Sort.by(orders);
    }
}
