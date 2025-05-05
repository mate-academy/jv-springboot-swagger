package mate.academy.springboot.swagger.service;

import org.springframework.data.domain.Sort;

public interface SortParser {
    Sort getProductSorter(String orderBy);
}
