package mate.academy.springboot.swagger.service;

import org.springframework.data.domain.Sort;

public interface SortingOptionsParser {
    Sort parse(String sortBy);
}
