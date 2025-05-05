package mate.academy.springboot.swagger.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class SortServiceImpl implements SortService {
    @Override
    public List<Sort.Order> parseSortedOrders(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(":")) {
            String[] sortingFields = sortBy.split(";");
            for (String field : sortingFields) {
                orders.add(getSortOrder(field));
            }
        } else {
            Sort.Order order = new Sort.Order(Sort.Direction.DESC, sortBy);
            orders.add(order);
        }
        return orders;
    }

    private Sort.Order getSortOrder(String field) {
        Sort.Order order;
        if (field.contains(":")) {
            String[] fieldsAndDirections = field.split(":");
            order = new Sort.Order(Sort.Direction.valueOf(fieldsAndDirections[1]),
                    fieldsAndDirections[0]);
        } else {
            order = new Sort.Order(Sort.Direction.DESC, field);
        }
        return order;
    }
}
