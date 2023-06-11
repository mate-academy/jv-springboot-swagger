package mate.academy.springboot.swagger.util.impl;

import java.util.ArrayList;
import java.util.List;
import mate.academy.springboot.swagger.util.SortUtil;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class SortUtilImpl implements SortUtil {
    private static final int FIELD_INDEX = 0;
    private static final int DIRECTION_INDEX = 1;

    @Override
    public List<Sort.Order> parse(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(";")) {
            String[] fields = sortBy.split(";");
            for (String field: fields) {
                Sort.Order order;
                if (field.contains(":")) {
                    String[] fieldsAndDirections = field.split(":");
                    order = new Sort.Order(Sort.Direction
                            .valueOf(fieldsAndDirections[DIRECTION_INDEX]),
                            fieldsAndDirections[FIELD_INDEX]);
                } else {
                    order = new Sort.Order(Sort.Direction.DESC, field);
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
