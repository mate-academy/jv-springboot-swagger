package mate.academy.springboot.swagger.service;

import java.util.List;
import org.springframework.data.domain.Sort;

public interface SortingService {
    List<Sort.Order> getAllSorting(String sortBy);
}
