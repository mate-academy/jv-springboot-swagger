package mate.academy.springboot.swagger.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class SortService {
    private static final String SEPARATOR_FIELDS = ";";
    private static final String SEPARATOR_ORDER = ":";

    public Sort getSort(String orderBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (orderBy.contains(":")) {
            String[] arrayOrders = orderBy.split(SEPARATOR_FIELDS);
            for (String field: arrayOrders) {
                Sort.Order order;
                if (field.contains(":")) {
                    String[] fieldAndDirection = field.split(SEPARATOR_ORDER);
                    order = new Sort.Order(
                            Sort.Direction.valueOf(fieldAndDirection[1]),
                            fieldAndDirection[0]);
                } else {
                    order = new Sort.Order(Sort.Direction.ASC, field);
                }
                orders.add(order);
            }
        } else {
            Sort.Order order = new Sort.Order(Sort.Direction.ASC, orderBy);
            orders.add(order);
        }
        return Sort.by(orders);
    }
}
