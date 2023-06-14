package mate.academy.springboot.swagger.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;

public class ParserUtil {
    public static List<Sort.Order> parse(String sort) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sort.contains(":")) {
            String[] sortParams = sort.split(";");
            for (String sortParam : sortParams) {
                Sort.Order order;
                if (sortParam.contains(":")) {
                    String[] sortValues = sortParam.split(":");
                    order = new Sort.Order(Sort.Direction.fromString(sortValues[1]), sortValues[0]);
                } else {
                    order = new Sort.Order(Sort.Direction.DESC, sortParam);
                }
                orders.add(order);
            }
        } else {
            orders.add(new Sort.Order(Sort.Direction.DESC, sort));
        }
        return orders;
    }
}
