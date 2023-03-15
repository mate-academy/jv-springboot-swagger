package mate.academy.springboot.swagger.service;

import org.springframework.data.domain.PageRequest;

public interface SortService {
    PageRequest getPageRequest(Integer count, Integer page, String sortBy);
}
