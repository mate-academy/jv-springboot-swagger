package mate.academy.springboot.swagger.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class SortUtilImpl implements SortUtil {
    @Override
    public List<Sort.Order> getSortParams(String sortBy) {
        List<Sort.Order> sortParams = new ArrayList<>();
        Sort.Order order;
        if (sortBy.contains(":")) {
            String[] sortingFields = sortBy.split(";");
            for (String field : sortingFields) {
                if (field.contains(":")) {
                    String[] fieldAndDirection = field.split(":");
                    order = new Sort.Order(Sort.Direction.valueOf(fieldAndDirection[1]),
                            fieldAndDirection[0]);
                } else {
                    order = new Sort.Order(Sort.Direction.DESC, field);
                }
                sortParams.add(order);
            }
        } else {
            order = new Sort.Order(Sort.Direction.DESC, sortBy);
            sortParams.add(order);
        }
        return sortParams;
    }
}
