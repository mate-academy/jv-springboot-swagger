package mate.academy.springboot.swagger.service.handler;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class PaginationAndSortingHandler {

    public Pageable handle(String page,
                           String count,
                           String sortBy) {
        int p = Integer.parseInt(page) - 1;
        int c = Integer.parseInt(count);
        Sort sort = getSort(sortBy);
        if (p < 0) {
            throw new IllegalArgumentException("Parameter page must be > 0");
        }
        return PageRequest.of(p, c, sort);
    }

    private Sort getSort(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(":")) {
            String[] sortingFields = sortBy.split(";");
            for (String field : sortingFields) {
                Sort.Order order;
                if (field.contains(":")) {
                    String[] fieldsAndDirection = field.split(":");
                    String direction = fieldsAndDirection[1].toUpperCase();
                    order = new Sort.Order(Sort.Direction.valueOf(direction),
                            fieldsAndDirection[0]);
                } else {
                    order = new Sort.Order(Sort.Direction.ASC, field);
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
