package mate.academy.springboot.swagger.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ParseServiceImpl implements ParseService {
    private static final String FIELDS_SEPARATOR = ";";
    private static final String DIRECTION_SEPARATOR = ":";

    @Override
    public Sort directionParcing(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(DIRECTION_SEPARATOR)) {
            String[] fields = sortBy.split(FIELDS_SEPARATOR);
            for (String field : fields) {
                Sort.Order order;
                if (field.contains(DIRECTION_SEPARATOR)) {
                    String[] fieidsAndDirection = field.split(DIRECTION_SEPARATOR);
                    order = new Sort.Order(Sort.Direction.valueOf(fieidsAndDirection[1]),
                            fieidsAndDirection[0]);
                } else {
                    order = new Sort.Order(Sort.Direction.DESC, field);
                }
                orders.add(order);
            }
        }
        return Sort.by(orders);
    }
}
