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
    private static final String SEMICOLON = ";";
    private static final String COLON = ":";

    @Override
    public PageRequest getPageRequest(Integer count, Integer page, String sortBy) {
        if (!sortBy.contains(COLON) & sortBy.contains(SEMICOLON)) {
            List<Sort.Order> orders = Arrays.stream(sortBy.split(SEMICOLON))
                    .map(field -> new Sort.Order(Sort.Direction.DESC, field))
                    .collect(Collectors.toList());
            return PageRequest.of(page,count, Sort.by(orders));
        }
        if (!sortBy.contains(COLON)) {
            return PageRequest.of(page, count,
                    Sort.by(new Sort.Order(Sort.Direction.DESC, sortBy)));
        }
        List<Sort.Order> orders = Arrays.stream(sortBy.split(SEMICOLON))
                    .map(fieldsAndOrder -> fieldsAndOrder.contains(COLON)
                            ? new Sort.Order(Sort.Direction.valueOf(fieldsAndOrder.split(COLON)[1]),
                            fieldsAndOrder.split(COLON)[0])
                            : new Sort.Order(Sort.Direction.DESC, fieldsAndOrder))
                    .collect(Collectors.toList());
        return PageRequest.of(page, count, Sort.by(orders));

    }
}
