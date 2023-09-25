package mate.academy.springboot.swagger.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class SortingParamParser {
    private static final String FIELD_SEPARATOR = ";";
    private static final String DIRECTION_SEPARATOR = ":";
    private static final int FIELD_INDEX = 0;
    private static final int DIRECTION_INDEX = 1;

    public Sort parse(String params) {
        List<Sort.Order> orders = new ArrayList<>();
        for (String field : params.split(FIELD_SEPARATOR)) {
            if (!field.contains(DIRECTION_SEPARATOR)) {
                orders.add(new Sort.Order(Sort.Direction.ASC, field));
                continue;
            }
            String[] fieldAndDirection = field.split(DIRECTION_SEPARATOR);
            orders.add(new Sort.Order(
                    Sort.Direction.valueOf(fieldAndDirection[DIRECTION_INDEX]),
                    fieldAndDirection[FIELD_INDEX]));
        }
        return Sort.by(orders);
    }
}
