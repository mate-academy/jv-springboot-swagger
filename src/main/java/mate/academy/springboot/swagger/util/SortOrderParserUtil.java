package mate.academy.springboot.swagger.util;

import org.springframework.data.domain.Sort;

public class SortOrderParserUtil {
    public static Sort getSort(String sort) {
        Sort.Order order;
        if (sort.contains(":")) {
            String[] propertyAndDirection = sort.split(":");
            order = new Sort.Order(Sort.Direction.valueOf(propertyAndDirection[1].toUpperCase()),
                    propertyAndDirection[0]);
            return Sort.by(order);
        }
        return Sort.by(sort);
    }
}
