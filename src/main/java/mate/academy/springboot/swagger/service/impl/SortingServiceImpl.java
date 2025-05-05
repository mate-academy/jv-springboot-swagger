package mate.academy.springboot.swagger.service.impl;

import java.util.ArrayList;
import java.util.List;
import mate.academy.springboot.swagger.service.SortingService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class SortingServiceImpl implements SortingService {
    public static final String VALUE_SEPARATOR = ";";
    public static final String VALUE_POINTER = ":";

    @Override
    public PageRequest getPageRequest(Integer count, Integer page, String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(VALUE_POINTER)) {
            String[] sortingFields = sortBy.split(VALUE_SEPARATOR);
            for (String field: sortingFields) {
                Sort.Order order;
                if (field.contains(VALUE_POINTER)) {
                    String[] fieldsAndDirections = field.split(VALUE_POINTER);
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
        Sort sort = Sort.by(orders);
        return PageRequest.of(page, count, sort);
    }
}
