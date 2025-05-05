package mate.academy.springboot.swagger.service;

import org.springframework.data.domain.Sort;

public interface SortParser {
    Sort parse(String sortBy);
}
