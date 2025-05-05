package mate.academy.springboot.swagger.util;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class SortProductUtil {
    private static final Integer DIRECTION_INDEX = 1;
    private static final Integer FIELD_INDEX = 0;
    private static List<String> productSortingFields;

    @PostConstruct
    private void postConstruct() {
        productSortingFields = new ArrayList<>();
        productSortingFields.add("title");
        productSortingFields.add("price");
        productSortingFields.add("none");
    }

    public Sort getSort(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy != null && !sortBy.equals("none")) {
            String[] sortingFields = sortBy.split(";");
            for (String sortingField : sortingFields) {
                Sort.Order order;
                if (sortingField.contains(":")
                        && productSortingFields.contains(sortingField.split(":")[FIELD_INDEX])) {
                    String[] fieldsAndDirections = sortingField.split(":");
                    order = new Sort.Order(
                            Sort.Direction.valueOf(fieldsAndDirections[DIRECTION_INDEX]),
                            fieldsAndDirections[FIELD_INDEX]);
                } else if (productSortingFields.contains(sortingField)) {
                    order = new Sort.Order(Sort.Direction.ASC, sortingField);
                } else {
                    throw new RuntimeException("Invalid sorting parameter");
                }
                orders.add(order);
            }
        }
        return Sort.by(orders);
    }
}
