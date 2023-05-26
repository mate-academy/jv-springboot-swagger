package mate.academy.springboot.swagger.util;

import java.util.List;
import org.springframework.data.domain.Sort;

public interface SortService {
    List<Sort.Order> getSortOrders(String sortBy);
}
