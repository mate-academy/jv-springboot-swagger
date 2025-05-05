package mate.academy.springboot.swagger.service;

import java.util.List;
import org.springframework.data.domain.Sort;

public interface SortOrderService {
    List<Sort.Order> sortOrders(String sortBy);
}

