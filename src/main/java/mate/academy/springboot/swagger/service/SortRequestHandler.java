package mate.academy.springboot.swagger.service;

import java.util.List;
import org.springframework.data.domain.Sort;

public interface SortRequestHandler {
    List<Sort.Order> parseSortOrders(String sortBy);
}
