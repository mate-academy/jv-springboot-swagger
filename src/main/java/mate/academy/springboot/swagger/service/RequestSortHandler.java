package mate.academy.springboot.swagger.service;

import java.util.List;
import org.springframework.data.domain.Sort;

public interface RequestSortHandler {
    List<Sort.Order> getSortProductsByDirection(String sortBy);
}
