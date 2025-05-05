package mate.academy.springboot.swagger.parser;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class ProductUrlParser {
    private static final String COLON = ":";
    private static final String SEMICOLON = ";";
    private static int DIRECTION = 1;
    private static int FIELD = 0;

    public List<Sort.Order> parse(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(":")) {
            String[] sortingFields = sortBy.split(SEMICOLON);
            for (String field : sortingFields) {
                Sort.Order order;
                if (field.contains(":")) {
                    String[] fieldAndDirection = field.split(COLON);
                    order = new Sort.Order(Sort.Direction.valueOf(
                            fieldAndDirection[DIRECTION]),
                            fieldAndDirection[FIELD]);
                } else {
                    order = new Sort.Order(Sort.Direction.ASC, field);
                }
                orders.add(order);
            }
        } else {
            Sort.Order order = new Sort.Order(Sort.Direction.ASC, sortBy);
            orders.add(order);
        }
        return orders;
    }
}
