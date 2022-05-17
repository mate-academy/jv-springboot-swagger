package mate.academy.springboot.swagger.service.impl;

import java.util.ArrayList;
import java.util.List;
import mate.academy.springboot.swagger.service.RequestStringHandler;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class RequestStringHandlerImpl implements RequestStringHandler {
    private static final String DELIMITER = ":";
    private static final String SEPARATOR = ";";

    @Override
    public List<Sort.Order> parseSortingCondition(String sortCondition) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortCondition.contains(DELIMITER)) {
            String[] sortingFields = sortCondition.split(SEPARATOR);
            for (String field : sortingFields) {
                orders.add(getCombinedOrder(field));
            }
        } else {
            Sort.Order order = new Sort.Order(Sort.Direction.DESC, sortCondition);
            orders.add(order);
        }
        return orders;
    }

    private Sort.Order getCombinedOrder(String field) {
        Sort.Order order;
        if (field.contains(DELIMITER)) {
            String[] fieldsAndDirections = field.split(DELIMITER);
            order = new Sort.Order(Sort.Direction.valueOf(fieldsAndDirections[1]),
                    fieldsAndDirections[0]);
        } else {
            order = new Sort.Order(Sort.Direction.DESC, field);
        }
        return order;
    }
}
