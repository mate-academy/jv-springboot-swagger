package mate.academy.springboot.swagger.service.impl;

import java.util.ArrayList;
import java.util.List;
import mate.academy.springboot.swagger.service.SortParamsParser;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class SortParamsParserImpl implements SortParamsParser {
    private static final String FIELD_SEPARATOR = ";";
    private static final String FIELD_AND_DIRECTION_SEPARATOR = ":";

    @Override
    public Sort getSortForParams(String params) {
        List<Sort.Order> orders = new ArrayList<>();
        String[] sortingFields = params.split(FIELD_SEPARATOR);
        for (String field : sortingFields) {
            Sort.Order order;
            if (field.contains(FIELD_AND_DIRECTION_SEPARATOR)) {
                String[] fieldsAndDirections = field.split(FIELD_AND_DIRECTION_SEPARATOR);
                order = new Sort.Order(Sort.Direction.valueOf(fieldsAndDirections[1]),
                        fieldsAndDirections[0]);
            } else {
                order = new Sort.Order(Sort.Direction.ASC, field);
            }
            orders.add(order);
        }
        return Sort.by(orders);
    }
}
