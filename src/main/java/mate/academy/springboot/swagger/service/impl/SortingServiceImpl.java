package mate.academy.springboot.swagger.service.impl;

import java.util.ArrayList;
import java.util.List;
import mate.academy.springboot.swagger.service.SortingService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class SortingServiceImpl implements SortingService {

    @Override
    public List<Sort.Order> sortBy(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(":")) {
            String[] sortingFields = sortBy.split(";");
            for (String fields : sortingFields) {
                Sort.Order order;
                if (fields.contains(":")) {
                    String[] fieldsAndDirections = sortBy.split(":");
                    order = new Sort.Order(Sort.Direction.valueOf(fieldsAndDirections[1]),
                            fieldsAndDirections[0]);
                } else {
                    order = new Sort.Order(Sort.Direction.DESC, fields);
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
