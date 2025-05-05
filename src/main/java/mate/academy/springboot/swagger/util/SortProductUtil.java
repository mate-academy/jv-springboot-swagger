package mate.academy.springboot.swagger.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class SortProductUtil {
    private static final int FIELD_INDEX = 0;
    private static final int DIRECTION_INDEX = 1;
    private static final String SPLITTER_ONE = ";";
    private static final String SPLITTER_TWO = ":";

    public static List<Sort.Order> getSortingProducts(String sortParameter) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortParameter.contains(SPLITTER_TWO)) {
            String[] splitFields = sortParameter.split(SPLITTER_ONE);
            for (String field : splitFields) {
                Sort.Order order;
                if (field.contains(SPLITTER_TWO)) {
                    String[] fieldAndDirection = field.split(SPLITTER_TWO);
                    order = new Sort.Order(Sort.Direction.valueOf(
                            fieldAndDirection[DIRECTION_INDEX]),
                            fieldAndDirection[FIELD_INDEX]);
                } else {
                    order = new Sort.Order(Sort.Direction.DESC, field);
                }
                orders.add(order);
            }
        } else {
            Sort.Order order = new Sort.Order(Sort.Direction.DESC, sortParameter);
            orders.add(order);
        }
        return orders;
    }
}
