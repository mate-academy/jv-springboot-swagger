package mate.academy.springboot.swagger.service.impl;

import java.util.ArrayList;
import java.util.List;
import mate.academy.springboot.swagger.service.SortParserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class SortParserServiceImpl implements SortParserService {
    private static final String COLON = ":";
    private static final String SEMI_COLON = ";";
    private static final int ORDER_TYPE = 1;
    private static final int SORT_TYPE = 0;

    public PageRequest createPageRequest(Integer count, Integer page, String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(COLON)) {
            String[] sortingFields = sortBy.split(SEMI_COLON);
            for (String field : sortingFields) {
                Sort.Order order;
                if (field.contains(COLON)) {
                    String[] fieldAndDirections = field.split(COLON);
                    order = new Sort.Order(Sort.Direction.valueOf(fieldAndDirections[ORDER_TYPE]),
                            fieldAndDirections[SORT_TYPE]);
                } else {
                    order = new Sort.Order(Sort.Direction.ASC, field);
                }
                orders.add(order);
            }
        } else {
            Sort.Order order = new Sort.Order(Sort.Direction.ASC, sortBy);
            orders.add(order);
        }
        Sort sort = Sort.by(orders);
        return PageRequest.of(page, count, sort);
    }
}
