package mate.academy.springboot.swagger.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class ParserUtil {
    private static final String TWO_DOTS = ":";
    private static final String SEPARATOR = ";";
    private static final int FIELD = 0;    
    private static final int DIRECTION = 1;
    
    public List<Sort.Order> getOrders(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(TWO_DOTS) || sortBy.contains(SEPARATOR)) {
            String[] sortingFields = sortBy.split(SEPARATOR);
            for (String field : sortingFields) {
                Sort.Order order;
                if (field.contains(TWO_DOTS)) {
                    String[] fieldsAndDirections = field.split(TWO_DOTS);
                    order = new Sort.Order(Sort.Direction.valueOf(fieldsAndDirections[DIRECTION]),
                            fieldsAndDirections[FIELD]);
                } else {
                    order = new Sort.Order(Sort.Direction.DESC, field);
                }
                orders.add(order);
            }
        } else {
            Sort.Order order = new Sort.Order(Sort.Direction.DESC, sortBy);
        }
        return orders;
    }
}
