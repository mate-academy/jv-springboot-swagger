package mate.academy.springboot.swagger.service;

import org.springframework.data.domain.Sort;

public interface SortParamParser {
    Sort parse(String sortParam);
}
