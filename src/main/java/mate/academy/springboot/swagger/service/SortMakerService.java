package mate.academy.springboot.swagger.service;

import org.springframework.data.domain.Sort;

public interface SortMakerService {
    Sort sort(String sortBy);
}
