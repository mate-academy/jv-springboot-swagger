package mate.academy.springboot.swagger.util;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Component;

@Component
public class PaginationUtil {
    private static final Direction DEFAULT_SORTING_DIRECTION = Direction.DESC;
    private static final Pattern PATTERN_ITEMS_SPLITTER = Pattern.compile(";");
    private static final Pattern PATTERN_NAME_DIRECTION = Pattern.compile(":");
    private static final int INDEX_ITEM_SORT_BY = 0;
    private static final int INDEX_DIRECTION = 1;

    public Pageable getPagination(Integer page, Integer count, String sortBy) {
        return PageRequest.of(page, count, getSorting(sortBy));
    }

    private static Sort getSorting(String sortBy) {
        return Sort.by(getOrders(sortBy));
    }

    private static List<Order> getOrders(String sortBy) {
        return Arrays.stream(PATTERN_ITEMS_SPLITTER.split(sortBy)).map(item -> {
            String[] itemAndSorting = PATTERN_NAME_DIRECTION.split(item);
            Direction direction = itemAndSorting.length == 2
                                  ? Direction.fromString(itemAndSorting[INDEX_DIRECTION])
                                  : DEFAULT_SORTING_DIRECTION;
            return new Order(
                    direction,
                    itemAndSorting[INDEX_ITEM_SORT_BY]
            );
        }).collect(Collectors.toList());
    }
}
