package mate.academy.springboot.swagger.service;

import org.springframework.data.domain.PageRequest;

public interface SortParserService {
    PageRequest createPageRequest(Integer count, Integer page, String sortBy);
}
