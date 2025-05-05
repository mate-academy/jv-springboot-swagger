package mate.academy.springboot.swagger.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public final class PaginationUtil {
    private static final int PROPERTY_INDEX = 0;
    private static final int DIRECTION_INDEX = 1;
    private static final String ORDER_PROPERTIES_DIVISOR = ";";
    private static final String ORDER_AND_DIRECTION_DIVISOR = ":";

    private PaginationUtil(){
    }

    public static Pageable getPageableForParameters(Integer size, Integer page, String sort) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sort.contains(":")) {
            String[] properties = sort.split(ORDER_PROPERTIES_DIVISOR);
            for (String property : properties) {
                if (property.contains(ORDER_AND_DIRECTION_DIVISOR)) {
                    String[] orderAndDirection = property.split(ORDER_AND_DIRECTION_DIVISOR);
                    orders.add(new Sort.Order(Sort.Direction
                            .fromString(orderAndDirection[DIRECTION_INDEX]),
                            orderAndDirection[PROPERTY_INDEX]));
                } else {
                    orders.add(new Sort.Order(Sort.Direction.DESC, property));
                }
            }
        } else {
            orders.add(new Sort.Order(Sort.Direction.DESC, sort));
        }
        return PageRequest.of(page, size, Sort.by(orders));
    }
}
