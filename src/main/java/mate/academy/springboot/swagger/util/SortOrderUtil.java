package mate.academy.springboot.swagger.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class SortOrderUtil {
    public List<Sort.Order> getSortOrders(String sortBy) {
        List<Sort.Order> sortOrders = new ArrayList<>();
        if (sortBy.contains(":")) {
            String[] sortingFields = sortBy.split(";");
            for (String sortingField : sortingFields) {
                Sort.Order order;
                if (sortingField.contains(":")) {
                    String[] fieldsAndDirections = sortingField.split(":");
                    order = new Sort.Order(Sort.Direction.valueOf(fieldsAndDirections[1]),
                            fieldsAndDirections[0]);
                } else {
                    order = new Sort.Order(Sort.Direction.ASC, sortingField);
                }
                sortOrders.add(order);
            }
        } else {
            sortOrders.add(new Sort.Order(Sort.Direction.ASC, sortBy));
        }
        return sortOrders;
    }
}
