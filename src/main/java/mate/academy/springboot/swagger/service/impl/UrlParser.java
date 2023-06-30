package mate.academy.springboot.swagger.service.impl;

import java.util.ArrayList;
import java.util.List;
import mate.academy.springboot.swagger.service.Parser;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class UrlParser implements Parser<Sort.Order, String> {
    private static final String COLON = ":";
    private static final String SEMICOLON = ";";
    private static final int INDEX_FOR_DIRECTION = 1;
    private static final int INDEX_FOR_FIELD = 0;

    @Override
    public List<Sort.Order> parse(String value) {
        List<Sort.Order> orders = new ArrayList<>();
        if (value.contains(COLON)) {
            String[] sortingFields = value.split(SEMICOLON);
            for (String field : sortingFields) {
                Sort.Order order;
                if (field.contains(COLON)) {
                    String[] fieldAndDirection = field.split(COLON);
                    order = new Sort.Order(Sort.Direction
                            .valueOf(fieldAndDirection[INDEX_FOR_DIRECTION]),
                            fieldAndDirection[INDEX_FOR_FIELD]);
                } else {
                    order = new Sort.Order(Sort.Direction.DESC, field);
                }
                orders.add(order);
            }
        } else {
            Sort.Order order = new Sort.Order(Sort.Direction.DESC, value);
            orders.add(order);
        }
        return orders;
    }
}
