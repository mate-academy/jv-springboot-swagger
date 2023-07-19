package mate.academy.springboot.swagger.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;

public class SortUtil {
    public static List<Sort.Order> getSort(String requestParam) {
        List<Sort.Order> orders = new ArrayList<>();
        if (requestParam.contains(":")) {
            String[] sortingFields = requestParam.split(";");
            for (String field: sortingFields) {
                Sort.Order order;
                if (field.contains(":")) {
                    String[] fieldAndDirections = field.split(":");
                    order = new Sort.Order(Sort.Direction.valueOf(fieldAndDirections[1]),
                            fieldAndDirections[0]);
                } else {
                    order = new Sort.Order(Sort.Direction.DESC, field);
                }
                orders.add(order);
            }
        } else {
            Sort.Order order = new Sort.Order(Sort.Direction.DESC, requestParam);
        }
        return orders;
    }
}
