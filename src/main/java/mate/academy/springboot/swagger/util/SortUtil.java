package mate.academy.springboot.swagger.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;

public class SortUtil {
    private static final String SEMICOLON = ";";
    private static final String COLON = ":";

    private SortUtil() {
    }

    public static List<Sort.Order> parse(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(COLON)) {
            String[] sortingFields = sortBy.split(SEMICOLON);
            for (String field : sortingFields) {
                Sort.Order order;
                if (field.contains(COLON)) {
                    String[] fieldAndDirection = field.split(COLON);
                    order = new Sort.Order(
                            Sort.Direction.valueOf(fieldAndDirection[1]), fieldAndDirection[0]);
                } else {
                    order = new Sort.Order(Sort.Direction.DESC, field);
                }
                orders.add(order);
            }
        } else {
            orders.add(new Sort.Order(Sort.Direction.DESC, sortBy));
        }
        return orders;
    }
}
