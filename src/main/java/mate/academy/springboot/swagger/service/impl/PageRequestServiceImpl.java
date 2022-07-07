package mate.academy.springboot.swagger.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.service.PageRequestService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PageRequestServiceImpl implements PageRequestService {
    private static final String COLON = ":";
    private static final String SEMICOLON = ";";
    private static final int DIRECTION_INDEX = 1;
    private static final int PARAMETER_INDEX = 0;

    @Override
    public PageRequest getPageRequest(Integer page, Integer size, String sortBy) {
        return PageRequest.of(page, size, getSort(sortBy));
    }

    private Sort getSort(String sortBy) {
        List<Sort.Order> orders = Arrays.stream(sortBy.split(SEMICOLON))
                .map(s -> !s.contains(COLON)
                        ? Sort.Order.asc(s)
                        : new Sort.Order(Sort.Direction
                                                .fromString(s.split(COLON)[DIRECTION_INDEX]),
                        s.split(COLON)[PARAMETER_INDEX]))
                .collect(Collectors.toList());
        return Sort.by(orders);
    }
}
