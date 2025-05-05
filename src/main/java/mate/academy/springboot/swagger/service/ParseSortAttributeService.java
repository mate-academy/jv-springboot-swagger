package mate.academy.springboot.swagger.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;

public class ParseSortAttributeService {
    public static Sort parseSortParams(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(":")) {
            String[] sortParams = sortBy.split(";");
            for (String param : sortParams) {
                Sort.Order order;
                if (param.contains(":")) {
                    String[] paramAndDirection = param.split(":");
                    order = new Sort.Order(Sort.Direction.valueOf(paramAndDirection[1]),
                            paramAndDirection[0]);
                } else {
                    order = new Sort.Order(Sort.Direction.ASC, param);
                }
                orders.add(order);
            }
        } else {
            Sort.Order order = new Sort.Order(Sort.Direction.ASC, sortBy);
            orders.add(order);
        }
        return Sort.by(orders);
    }
}
