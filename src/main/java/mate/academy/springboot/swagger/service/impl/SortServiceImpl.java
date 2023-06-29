package mate.academy.springboot.swagger.service.impl;

import java.util.ArrayList;
import java.util.List;
import mate.academy.springboot.swagger.service.SortService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class SortServiceImpl implements SortService {
    private static final String DELIMITER_COLON = ":";
    private static final String DELIMITER_SEMICOLON = ";";

    @Override
    public List<Sort.Order> getSortParams(String sortBy) {
        List<Sort.Order> sortParams = new ArrayList<>();
        Sort.Order order;
        if (sortBy.contains(DELIMITER_COLON)) {
            String[] sortingFields = sortBy.split(DELIMITER_SEMICOLON);
            for (String field : sortingFields) {
                if (field.contains(DELIMITER_COLON)) {
                    String[] fieldAndDirection = field.split(DELIMITER_COLON);
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
