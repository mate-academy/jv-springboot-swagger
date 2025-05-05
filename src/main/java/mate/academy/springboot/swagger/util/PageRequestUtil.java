package mate.academy.springboot.swagger.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class PageRequestUtil {
    private static final Integer DIRECTION_INDEX = 1;
    private static final Integer PROPERTY_INDEX = 0;
    private static final String SPLIT_DELIMITER = ":";

    public PageRequest getPageRequest(Integer count,
                                      Integer page,
                                      String sortBy,
                                      String... sortingOptions) {
        List<String> options = List.of(sortingOptions);
        List<String> sortParam = new ArrayList<>();
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(SPLIT_DELIMITER)) {
            String[] sortingFields = sortBy.split(";");
            for (String field : sortingFields) {
                Sort.Order order;
                if (field.contains(SPLIT_DELIMITER)) {
                    String[] fieldsAndDirections = field.split(SPLIT_DELIMITER);
                    sortParam.add(fieldsAndDirections[PROPERTY_INDEX]);
                    order = new Sort.Order(
                            Sort.Direction.valueOf(fieldsAndDirections[DIRECTION_INDEX]),
                            fieldsAndDirections[PROPERTY_INDEX]);
                } else {
                    order = new Sort.Order(Sort.Direction.DESC, field);
                }
                orders.add(order);
            }
        } else {
            orders.add(new Sort.Order(Sort.Direction.DESC, sortBy));
        }
        for (int i = 0; i < sortParam.size(); i++) {
            if (!options.contains(sortParam.get(i))) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
        }
        Sort sort = Sort.by(orders);
        return PageRequest.of(page, count, sort);
    }
}
