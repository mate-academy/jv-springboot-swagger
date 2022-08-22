package mate.academy.springboot.swagger.util;

import org.springframework.data.domain.Sort;

public interface SortUtil {
    Sort getSort(String sortBy);
}
