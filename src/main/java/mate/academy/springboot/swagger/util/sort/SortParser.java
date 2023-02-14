package mate.academy.springboot.swagger.util.sort;

import org.springframework.data.domain.Sort;

public interface SortParser {
    Sort parse(String sortBy);
}
