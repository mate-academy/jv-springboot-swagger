package mate.academy.springboot.swagger.service.impl;

import java.util.ArrayList;
import java.util.List;
import mate.academy.springboot.swagger.service.RequestSortHandler;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class RequestSortHandlerImpl implements RequestSortHandler {
    private static final String COLON = ":";
    private static final String SEMICOLON = ";";

    @Override
    public List<Sort.Order> getSortProductsByDirection(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(COLON)) {
            return getOrdersBySplit(sortBy, orders);
        } else {
            orders.add(new Sort.Order(Sort.Direction.ASC, sortBy));
            return orders;
        }
    }

    private List<Sort.Order> getOrdersBySplit(String sortBy, List<Sort.Order> orders) {
        String[] sortingFields = sortBy.split(SEMICOLON);
        String[] fieldsAndDirections;
        for (String field : sortingFields) {
            fieldsAndDirections = field.split(COLON);
            orders.add(new Sort.Order(Sort.Direction.valueOf(fieldsAndDirections[1]),
                    fieldsAndDirections[0]));
        }
        return orders;
    }
}
