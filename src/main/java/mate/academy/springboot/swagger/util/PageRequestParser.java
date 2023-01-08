package mate.academy.springboot.swagger.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Component;

@Component
public class PageRequestParser {
    public PageRequest parse(Integer page, Integer size, String sortBy) {
        List<Order> orders = new ArrayList<>();
        if (sortBy.contains(":")) {
            for (String field : sortBy.split(";")) {
                if (field.contains(":")) {
                    String[] fieldAndDirection = field.split(":");
                    orders.add(new Order(Sort.Direction.valueOf(fieldAndDirection[1]),
                            fieldAndDirection[0]));
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
