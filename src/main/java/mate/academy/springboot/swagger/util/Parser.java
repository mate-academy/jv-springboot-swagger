package mate.academy.springboot.swagger.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;

public class Parser {

    public static List<Sort.Order> getSortOrders(String urlParams) {
        List<Sort.Order> orders = new ArrayList<>();
        if (urlParams.contains(":")) {
            String[] sortingFields = urlParams.split(";");
            for (String field : sortingFields) {
                Sort.Order order;
                if (field.contains(":")) {
                    String[] fieldsAndDirections = field.split(":");
                    order = new Sort.Order(Sort.Direction
                            .valueOf(fieldsAndDirections[1]),
                            fieldsAndDirections[0]);
                } else {
                    order = new Sort.Order(Sort.Direction.DESC, field);
                }
                orders.add(order);
            }
        } else {
            orders.add(new Sort.Order(Sort.Direction.DESC, urlParams));
        }
        return orders;
    }
}
