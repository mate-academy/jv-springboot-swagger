package mate.academy.springboot.swagger.service.impl;

import java.util.ArrayList;
import java.util.List;
import mate.academy.springboot.swagger.service.SortParser;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class SortParserImpl implements SortParser {
    @Override
    public Sort getProductSorter(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(":")) {
            String[] sortingFields = sortBy.split(";");
            for (String field: sortingFields) {
                Sort.Order order;
                if (field.contains(":")) {
                    String[] fieldsAndDirections = field.split(":");
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
        return Sort.by(orders);
    }
}
