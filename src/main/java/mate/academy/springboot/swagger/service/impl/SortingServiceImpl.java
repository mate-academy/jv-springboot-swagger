package mate.academy.springboot.swagger.service.impl;

import java.util.ArrayList;
import java.util.List;
import mate.academy.springboot.swagger.service.SortingService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class SortingServiceImpl implements SortingService {
    private static final String DELIMITER = ":";
    private static final String REGEX = ";";
    private static final Integer FIELD = 0;
    private static final Integer ORDER = 1;

    @Override
    public List<Sort.Order> getSortingOrder(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(DELIMITER)) {
            String[] sortingFields = sortBy.split(REGEX);
            for (String filed : sortingFields) {
                Sort.Order order;
                if (filed.contains(DELIMITER)) {
                    String[] fieldsAndDirections = filed.split(DELIMITER);
                    order = new Sort.Order(Sort.Direction.valueOf(fieldsAndDirections[ORDER]),
                    fieldsAndDirections[FIELD]);
                } else {
                    order = new Sort.Order(Sort.Direction.DESC, filed);
                }
                orders.add(order);
            }
        } else {
            Sort.Order order = new Sort.Order(Sort.Direction.DESC, sortBy);
            orders.add(order);
        }
        return orders;
    }
}
