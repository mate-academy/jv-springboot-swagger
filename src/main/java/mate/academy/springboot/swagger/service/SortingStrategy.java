package mate.academy.springboot.swagger.service;

import java.util.List;
import org.springframework.data.domain.Sort;

public interface SortingStrategy {

    List<Sort.Order> getSortingOrders(String sortBy);
}
