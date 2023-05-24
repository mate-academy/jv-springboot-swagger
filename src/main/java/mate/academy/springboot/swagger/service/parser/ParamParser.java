package mate.academy.springboot.swagger.service.parser;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;

public class ParamParser {
    public static List<Sort.Order> sortOrders(String param) {
        List<Sort.Order> orders = new ArrayList<>();
        if (param.contains(":")) {
            String[] fields = param.split(";");
            for (String field : fields) {
                Sort.Order order;
                if (field.contains(":")) {
                    String[] fieldAndDirection = field.split(":");
                    order = new Sort.Order(Sort.Direction.valueOf(fieldAndDirection[1]),
                            fieldAndDirection[0]);
                } else {
                    order = new Sort.Order(Sort.Direction.DESC, field);
                }
                orders.add(order);
            }
        } else {
            Sort.Order order = new Sort.Order(Sort.Direction.DESC, param);
            orders.add(order);
        }
        return orders;
    }
}
