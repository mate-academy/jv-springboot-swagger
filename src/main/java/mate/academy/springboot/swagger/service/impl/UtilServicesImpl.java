package mate.academy.springboot.swagger.service.impl;

import java.util.ArrayList;
import java.util.List;
import mate.academy.springboot.swagger.service.UtilServices;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class UtilServicesImpl implements UtilServices {
    @Override
    public PageRequest getPageRequest(Integer count, Integer page, String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(":")) {
            String[] splitStrings = sortBy.split(";");
            for (String field: splitStrings) {
                Sort.Order order;
                if (field.contains(":")) {
                    String[] rawOrder = field.split(":");
                    order = new Sort.Order(Sort.Direction.valueOf(rawOrder[1]), rawOrder[0]);
                } else {
                    order = new Sort.Order(Sort.Direction.DESC, field);
                }
                orders.add(order);
            }
        } else {
            orders.add(new Sort.Order(Sort.Direction.DESC, sortBy));
        }
        Sort sort = Sort.by(orders);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return pageRequest;
    }
}
