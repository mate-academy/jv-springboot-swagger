package mate.academy.springboot.swagger.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class Sorter {
    public Sort getSortParams(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        // IDK why we need this part...        if (sortBy.contains(":")) {
        String[] sortingFields = sortBy.split(";");
        for (String field : sortingFields) {
            Sort.Order order;
            if (field.contains(":")) {
                String[] fieldsAndDirections = field.split(":");
                order = new Sort.Order(Sort.Direction.valueOf(fieldsAndDirections[1]),
                        fieldsAndDirections[0]);
            } else {
                order = new Sort.Order(Sort.Direction.ASC, field);
            }
            orders.add(order);
        }
        /*
        ... and this        } else {
            Sort.Order order = new Sort.Order(Sort.Direction.DESC, sortBy);
            orders.add(order);
            because request like "http://localhost:8080/products?sortBy=id;price" (without specifying the sort order) won't work
        }
        */
        return Sort.by(orders);
    }
}
