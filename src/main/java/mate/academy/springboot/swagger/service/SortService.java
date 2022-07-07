package mate.academy.springboot.swagger.service;

import org.springframework.data.domain.PageRequest;

public interface SortService {
    PageRequest getPageRequest(Integer page, Integer count, String sortBy);
}
