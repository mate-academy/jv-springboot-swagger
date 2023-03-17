package mate.academy.springboot.swagger.service.impl;

import java.util.ArrayList;
import java.util.List;
import mate.academy.springboot.swagger.service.SortService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class SortServiceImpl implements SortService {
    private static final int DIRECTION_INDEX = 1;
    private static final int FIELD_INDEX = 0;

    @Override
    public PageRequest getPageRequest(Integer count, Integer page, String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(":")) {
            String[] sortingFields = sortBy.split(";");
            for (String field : sortingFields) {
                Sort.Order order;
                if (field.contains(":")) {
                    String[] fieldsAndDirections = field.split(":");
                    Sort.Direction directionProperty
                            = Sort.Direction.valueOf(fieldsAndDirections[DIRECTION_INDEX]);
                    String fieldProperty = fieldsAndDirections[FIELD_INDEX];
                    order = new Sort.Order(directionProperty, fieldProperty);
                } else {
                    order = new Sort.Order(Sort.Direction.DESC, field);
                }
                orders.add(order);
            }
        } else {
            Sort.Order order = new Sort.Order(Sort.Direction.DESC, sortBy);
            orders.add(order);
        }
        Sort sort = Sort.by(sortBy);
        PageRequest pageRequest = PageRequest.of(count, page, sort);
        return pageRequest;
    }
}
