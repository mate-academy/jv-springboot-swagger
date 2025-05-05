package mate.academy.springboot.swagger.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public class Parser {
    public static Pageable getPageRequest(Integer page, Integer size, String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(";")) {
            String[] params = sortBy.split(";");
            for (String param: params) {
                orders.add(getOrder(param));
            }
        } else {
            orders.add(getOrder(sortBy));
        }
        Sort sort = Sort.by(orders);
        return PageRequest.of(page, size, sort);
    }

    private static Sort.Order getOrder(String str) {
        if (str.contains(":")) {
            String[] elems = str.split(":");
            return new Sort.Order(Direction.fromString(elems[1].toUpperCase()),elems[0]);
        } else {
            return Sort.Order.asc(str);
        }
    }
}
