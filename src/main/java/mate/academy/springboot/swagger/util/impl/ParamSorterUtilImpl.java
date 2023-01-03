package mate.academy.springboot.swagger.util.impl;

import java.util.ArrayList;
import java.util.List;
import mate.academy.springboot.swagger.util.ParamSorterUtil;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class ParamSorterUtilImpl implements ParamSorterUtil {
    @Override
    public Sort parse(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(":")) {
            String[] sortingFields = sortBy.split(";");
            for (String field : sortingFields) {
                Sort.Order order;
                String[] fieldsAndDirections = field.split(":");
                if (field.contains(":")) {
                    order = new Sort.Order(Sort.Direction.valueOf(
                            fieldsAndDirections[1].toUpperCase()),
                            fieldsAndDirections[0])
                            .ignoreCase();
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
