package mate.academy.springboot.swagger.util;

import org.springframework.data.domain.Sort;

public interface ParamSorterUtil {
    Sort parse(String sortBy);
}
