package mate.academy.springboot.swagger.service.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class PaginationAndSortingHandler {

    private final String[] fields;
    private final int defaultCount;
    private final int defaultPage;

    @Autowired
    public PaginationAndSortingHandler() {
        fields = new String[]{"page", "count", "sortBy"};
        defaultCount = 4;
        defaultPage = 0;
    }

    public Pageable handle(Map<String, String> params) {
        int page = params.containsKey(fields[0])
                ? (Integer.parseInt(params.get(fields[0])) - 1) :
                defaultPage;
        int count = params.containsKey(fields[1])
                ? Integer.parseInt(params.get(fields[1])) :
                defaultCount;
        Sort sort = params.containsKey(fields[2]) ? getSort(params) : Sort.unsorted();
        if (page < 0) {
            throw new IllegalArgumentException("Parameter page must be > 0");
        }
        return PageRequest.of(page, count, sort);
    }

    private Sort getSort(Map<String, String> params) {
        String sortBy = params.get(fields[2]);
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(":")) {
            String[] sortingFields = sortBy.split(";");
            for (String field : sortingFields) {
                Sort.Order order;
                if (field.contains(":")) {
                    String[] fieldsAndDirection = field.split(":");
                    String direction = fieldsAndDirection[1].toUpperCase();
                    order = new Sort.Order(Sort.Direction.valueOf(direction),
                            fieldsAndDirection[0]);
                } else {
                    order = new Sort.Order(Sort.Direction.ASC, field);
                }
                orders.add(order);
            }
        } else {
            Sort.Order order = new Sort.Order(Sort.Direction.ASC, sortBy);
            orders.add(order);
        }

        return Sort.by(orders);
    }

    public List<String> getFields() {
        return Arrays.stream(fields).collect(Collectors.toList());
    }

}
