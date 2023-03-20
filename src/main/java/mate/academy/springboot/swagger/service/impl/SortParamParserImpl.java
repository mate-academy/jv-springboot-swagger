package mate.academy.springboot.swagger.service.impl;

import java.util.ArrayList;
import java.util.List;
import mate.academy.springboot.swagger.service.SortParamParser;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class SortParamParserImpl implements SortParamParser {
    private static final String FIELD_SEPARATOR = ";";
    private static final String FIELD_AND_DIRECTION_SEPARATOR = ":";
    @Override
    public Sort parse(String sortParam) {
        List<Sort.Order> orders = new ArrayList<>();
        String[] sortingFields = sortParam.split("FIELD_SEPARATOR");
        for (String field : sortingFields) {
            Sort.Order order;
            if (field.contains("FIELD_AND_DIRECTION_SEPARATOR")) {
                String[] fieldAndDirection = field.split("FIELD_AND_DIRECTION_SEPARATOR");
                order = new Sort.Order(Sort.Direction.valueOf(fieldAndDirection[1]),
                        fieldAndDirection[0]);
            } else {
                order = new Sort.Order(Sort.Direction.ASC, field);
            }
            orders.add(order);
        }
        return Sort.by(orders);
    }
}
