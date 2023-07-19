package mate.academy.springboot.swagger.util.impl;

import java.util.ArrayList;
import java.util.List;
import mate.academy.springboot.swagger.util.RequestProvider;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class RequestProviderImpl implements RequestProvider {
    @Override
    public PageRequest formPageRequest(Integer count, Integer page, String sortBy) {
        Sort sort = Sort.by(parseRequest(sortBy));

        return PageRequest.of(page, count, sort);
    }

    private List<Sort.Order> parseRequest(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();

        if (sortBy.contains(":")) {
            String[] sortingFields = sortBy.split(";");
            for (String field: sortingFields) {
                Sort.Order order;
                if (field.contains(":")) {
                    String[] fieldsAndDirections = field.split(":");
                    order = new Sort.Order(Sort.Direction.valueOf(
                            fieldsAndDirections[1]), fieldsAndDirections[0]);
                } else {
                    order = new Sort.Order(Sort.Direction.DESC, field);
                }
                orders.add(order);
            }
        } else {
            Sort.Order order = new Sort.Order(Sort.Direction.ASC, sortBy);
            orders.add(order);
        }

        return orders;
    }
}
