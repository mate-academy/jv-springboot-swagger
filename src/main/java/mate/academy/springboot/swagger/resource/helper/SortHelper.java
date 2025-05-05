package mate.academy.springboot.swagger.resource.helper;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;

public class SortHelper {
    private SortHelper() {
    }

    public static Sort parseSortOptions(String sortOptions) {
        if (sortOptions.contains(";")) {
            List<Sort.Order> orders = new ArrayList<>();
            for (String option : sortOptions.split(";")) {
                orders.add(parseSingleSortOption(option));
            }
            return Sort.by(orders);
        }
        return Sort.by(parseSingleSortOption(sortOptions));
    }

    private static Sort.Order parseSingleSortOption(String sortOption) {
        if (sortOption.contains(":")) {
            String[] propertyAndDirection = sortOption.split(":");
            return new Sort.Order(
                    Sort.Direction.valueOf(propertyAndDirection[1]),
                    propertyAndDirection[0]
            );
        }
        return Sort.Order.asc(sortOption);
    }
}
