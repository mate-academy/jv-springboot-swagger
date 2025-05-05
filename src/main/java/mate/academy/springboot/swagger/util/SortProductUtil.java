package mate.academy.springboot.swagger.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class SortProductUtil {
    private static final int FIELD_INDEX = 0;
    private static final int DIRECTION_INDEX = 1;

    public List<Sort.Order> getSortingProduct(String sortParameter) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortParameter.contains(":")) {
            String[] sortingFields = sortParameter.split(";");
            for (String field : sortingFields) {
                Sort.Order order;
                if (field.contains(":")) {
                    String[] fieldAndDirections = field.split(":");
                    order = new Sort.Order(Sort.Direction
                            .valueOf(fieldAndDirections[DIRECTION_INDEX]),
                            fieldAndDirections[FIELD_INDEX]);
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
