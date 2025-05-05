package mate.academy.springboot.swagger.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class SortingParser {
    public Sort parse(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(":")) {
            String[] fields = sortBy.split(";");
            for (String field : fields) {
                Sort.Order order;
                if (field.contains(":")) {
                    String[] fieldsAndDirections = field.split(":");
                    order = new Sort.Order(Sort.Direction.valueOf(fieldsAndDirections[1]),
                            fieldsAndDirections[0]);
                } else {
                    order = new Sort.Order(Sort.Direction.ASC, field);
                }
                orders.add(order);
            }
        }
        return Sort.by(orders);
    }
}
