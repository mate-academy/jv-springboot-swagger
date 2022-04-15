package mate.academy.springboot.swagger.service;

import java.util.List;
import org.springframework.data.domain.Sort;

public interface SortService {
    List<Sort.Order> parseSortingCondition(String sortCondition);
}
