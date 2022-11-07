package mate.academy.springboot.swagger.util;

import org.springframework.data.domain.Sort;

public interface SortingOptionsParser {
    Sort parse(String sortBy);
}
