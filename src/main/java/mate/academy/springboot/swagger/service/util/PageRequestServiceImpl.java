package mate.academy.springboot.swagger.service.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class PageRequestServiceImpl implements PageRequestService {
    private static final int DIRECTION_INDEX = 1;
    private static final int FIELD_INDEX = 0;

    @Override
    public PageRequest getPageRequest(int page, int count, String property) {
        List<Sort.Order> orders = new ArrayList<>();
        if (property.contains(":")) {
            String[] sortingFields = property.split(";");
            for (String field : sortingFields) {
                Sort.Order order;
                if (field.contains(":")) {
                    String[] fieldsAndDirections = field.split(":");
                    order = new Sort.Order(Sort.Direction.valueOf(
                            fieldsAndDirections[DIRECTION_INDEX]),
                            fieldsAndDirections[FIELD_INDEX]);
                    orders.add(order);
                } else {
                    order = new Sort.Order(Sort.Direction.ASC, field);
                    orders.add(order);
                }
            }
        } else {
            Sort.Order order = new Sort.Order(Sort.Direction.ASC, property);
            orders.add(order);
        }
        Sort sort = Sort.by(orders);
        return PageRequest.of(page, count, sort);
    }
}
