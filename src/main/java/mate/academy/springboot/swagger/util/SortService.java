package mate.academy.springboot.swagger.util;

import java.util.List;
import org.springframework.data.domain.Sort;

public interface SortService {
    public List<Sort.Order> sort(String sortBy);
}
