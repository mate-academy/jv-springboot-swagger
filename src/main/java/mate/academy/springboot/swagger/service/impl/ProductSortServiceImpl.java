package mate.academy.springboot.swagger.service.impl;

import mate.academy.springboot.swagger.service.ProductSortService;
import org.springframework.data.domain.Sort;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ProductSortServiceImpl implements ProductSortService {
    @Override
    public List<Sort.Order> sortBy(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(":")) {
            String[] sortingFields = sortBy.split(";");
            for (String field : sortingFields) {
                Sort.Order order;
                if (field.contains(":")) {
                    String[] fieldsAndDirections = field.split(":");
                    order = new Sort.Order(Sort.Direction.valueOf(fieldsAndDirections[1]),
                            fieldsAndDirections[0]);
                } else {
                    order = new Sort.Order(Sort.DEFAULT_DIRECTION, field);
                }
                orders.add(order);
            }
        } else {
            Sort.Order order = new Sort.Order(Sort.DEFAULT_DIRECTION, sortBy);
            orders.add(order);
        }
        return orders;
    }
}
