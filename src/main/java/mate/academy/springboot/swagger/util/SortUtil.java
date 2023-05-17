package mate.academy.springboot.swagger.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class SortUtil {
    private static final int DIRECTION_INDEX = 1;
    private static final int PARAMETER_INDEX = 0;

    public Sort getSort(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(";")) {
            String[] fields = sortBy.split(";");
            for (String field : fields) {
                orders.add(getOrder(field));
            }
        } else {
            orders.add(getOrder(sortBy));
        }
        return Sort.by(orders);
    }

    private Sort.Order getOrder(String sortBy) {
        Sort.Order order;
        if (sortBy.contains(":")) {
            String[] split = sortBy.split(":");
            order = new Sort.Order(Sort.Direction.valueOf(split[DIRECTION_INDEX]),
                    split[PARAMETER_INDEX]);
        } else {
            order = new Sort.Order(Sort.Direction.ASC, sortBy);
        }
        return order;
    }
}
