package mate.academy.springboot.swagger.util;

import java.util.List;
import org.springframework.data.domain.Sort;

public interface SortUtil {
    List<Sort.Order> parse(String sortBy);
}
