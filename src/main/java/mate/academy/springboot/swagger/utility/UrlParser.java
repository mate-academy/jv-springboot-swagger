package mate.academy.springboot.swagger.utility;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class UrlParser {
    private static final int DIRECTION_INDEX = 1;
    private static final int FIELD_INDEX = 0;

    public static PageRequest formPageRequest(Integer count, Integer page, String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(":")) {
            String[] sortingFields = sortBy.split(";");
            for (String field : sortingFields) {
                Sort.Order order;
                if (field.contains(":")) {
                    String[] fieldAndDirection = field.split(":");
                    order = new Sort.Order(Sort.Direction.valueOf(fieldAndDirection[DIRECTION_INDEX]),
                            fieldAndDirection[FIELD_INDEX]);

                } else {
                    order = new Sort.Order(Sort.Direction.DESC, field);
                }
                orders.add(order);
            }
        } else {
            Sort.Order order = new Sort.Order(Sort.Direction.ASC, sortBy);
            orders.add(order);
        }
        Sort sort = Sort.by(orders);
        return PageRequest.of(page,count,sort);
    }
}
