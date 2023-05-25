package mate.academy.springboot.swagger.service;

import org.springframework.data.domain.Sort;

public interface SortParamsParser {
    Sort getSortForParams(String params);
}
