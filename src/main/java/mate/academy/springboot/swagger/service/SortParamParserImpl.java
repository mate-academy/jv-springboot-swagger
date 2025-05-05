package mate.academy.springboot.swagger.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class SortParamParserImpl implements SortParamParser {
    @Override
    public Sort parse(String sortParam) {
        List<Sort.Order> orders = new ArrayList<>();
        String[] sortingFields = sortParam.split(";");
        for (String field : sortingFields) {
            Sort.Order order;
            if (field.contains(":")) {
                String[] fieldAndDirection = field.split(":");
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
