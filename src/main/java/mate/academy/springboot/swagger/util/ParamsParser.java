package mate.academy.springboot.swagger.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;

public class ParamsParser {
    private static final int PROPERTY_INDEX = 0;
    private static final int DIRECTION_INDEX = 1;
    private static final String SEMICOLON = ";";
    private static final String COLON = ":";

    public static Sort getSortingParams(String sortingParams) {
        List<Sort.Order> orders = new ArrayList<>();
        String[] sortingProperties = sortingParams.split(SEMICOLON);
        for (String sortingProperty : sortingProperties) {
            Sort.Order order;
            if (sortingProperty.contains(COLON)) {
                String[] valuesAndDirections = sortingProperty.split(COLON);
                order = new Sort.Order(
                        Sort.Direction.fromString(valuesAndDirections[DIRECTION_INDEX]),
                        valuesAndDirections[PROPERTY_INDEX]);
            } else {
                order = Sort.Order.asc(sortingProperty);
            }
            orders.add(order);
        }
        return Sort.by(orders);
    }
}
