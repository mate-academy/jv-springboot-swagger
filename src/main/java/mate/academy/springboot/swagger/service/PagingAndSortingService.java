package mate.academy.springboot.swagger.service;

import org.springframework.data.domain.PageRequest;

public interface PagingAndSortingService {
    PageRequest paginateAndSort(Integer count, Integer page, String sortBy);
}
