package mate.academy.springboot.swagger.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class ParserUtil {
    private static final String SEPARATOR1 = ":";
    private static final String SEPARATOR2 = ";";

    public List<Sort.Order> getOrders(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(SEPARATOR1)) {
            String[] sortingFields = sortBy.split(SEPARATOR2);
            for (String field : sortingFields) {
                Sort.Order order;
                if (field.contains(SEPARATOR2)) {
                    String[] fieldsAndDirections = field.split(SEPARATOR1);
                    order = new Sort.Order(Sort.Direction.valueOf(fieldsAndDirections[1]),
                            fieldsAndDirections[0]);
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
