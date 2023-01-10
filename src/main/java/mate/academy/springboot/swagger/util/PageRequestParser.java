package mate.academy.springboot.swagger.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Component;

@Component
public class PageRequestParser {
    private static final String COLON_REGEX = ":";
    private static final String SEMICOLON_REGEX = ";";
    private static final int ZERO_INDEX = 0;
    private static final int FIRST_INDEX = 1;

    public PageRequest parse(Integer page, Integer size, String sortBy) {
        List<Order> orders = new ArrayList<>();
        if (sortBy.contains(COLON_REGEX)) {
            for (String field : sortBy.split(SEMICOLON_REGEX)) {
                if (field.contains(COLON_REGEX)) {
                    String[] fieldAndDirection = field.split(COLON_REGEX);
                    orders.add(new Order(Sort.Direction.valueOf(
                            fieldAndDirection[FIRST_INDEX]),
                            fieldAndDirection[ZERO_INDEX]));
                } else {
                    orders.add(new Order(Sort.Direction.ASC, field));
                }
            }
        } else {
            orders.add(new Order(Sort.Direction.ASC, sortBy));
        }
        return PageRequest.of(page, size, Sort.by(orders));
    }
}
