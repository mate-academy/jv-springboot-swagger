package mate.academy.springboot.swagger.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.service.SortService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class SortServiceImpl implements SortService {
    @Override
    public PageRequest getPageRequest(Integer count, Integer page, String sortBy) {
        if (!sortBy.contains(":") & sortBy.contains(";")) {
            List<Sort.Order> orders = Arrays.stream(sortBy.split(";"))
                    .map(field -> new Sort.Order(Sort.Direction.DESC, field))
                    .collect(Collectors.toList());
            return PageRequest.of(page,count, Sort.by(orders));
        }
        if (!sortBy.contains(":")) {
            return PageRequest.of(page, count,
                    Sort.by(new Sort.Order(Sort.Direction.DESC, sortBy)));
        }
        List<Sort.Order> orders = Arrays.stream(sortBy.split(";"))
                    .map(fieldsAndOrder -> fieldsAndOrder.contains(":")
                            ? new Sort.Order(Sort.Direction.valueOf(fieldsAndOrder.split(":")[1]),
                            fieldsAndOrder.split(":")[0])
                            : new Sort.Order(Sort.Direction.DESC, fieldsAndOrder))
                    .collect(Collectors.toList());
        return PageRequest.of(page, count, Sort.by(orders));

    }
}
