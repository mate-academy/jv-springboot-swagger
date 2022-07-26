package mate.academy.springboot.swagger.service.sorting;

import org.springframework.data.domain.Sort;

public interface CustomSortingOrderBy {
    Sort sortBy(String sort);
}
