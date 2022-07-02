package mate.academy.springboot.service;

import java.util.List;
import org.springframework.data.domain.Sort;

public interface SortingService {
    List<Sort.Order> parseSortOrder(String sortBy);
}
