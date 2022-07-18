package mate.academy.springboot.swagger.service.impl;

import java.util.ArrayList;
import java.util.List;
import mate.academy.springboot.swagger.service.PageableProvider;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PageableProviderImpl implements PageableProvider {
    @Override
    public Pageable get(Integer count, Integer page, String sortBy) {
        PageRequest pageRequest;
        if (sortBy == null || sortBy.length() == 0) {
            pageRequest = PageRequest.of(page, count);
        } else {
            pageRequest = PageRequest.of(page, count, getSort(sortBy));
        }
        return pageRequest;
    }

    private Sort getSort(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        for (String token : sortBy.split(";")) {
            Sort.Order order;
            if (token.contains(":")) {
                String[] parameters = token.split(":");
                order = new Sort.Order(Sort.Direction.valueOf(parameters[1]), parameters[0]);
            } else {
                order = Sort.Order.asc(token);
            }
            orders.add(order);
        }
        return Sort.by(orders);
    }
}
