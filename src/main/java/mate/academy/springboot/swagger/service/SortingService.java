package mate.academy.springboot.swagger.service;

import org.springframework.data.domain.PageRequest;

public interface SortingService {
    PageRequest getPageRequest(Integer count, Integer page, String sortBy);
}
