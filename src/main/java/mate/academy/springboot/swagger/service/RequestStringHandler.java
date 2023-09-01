package mate.academy.springboot.swagger.service;

import java.util.List;
import org.springframework.data.domain.Sort;

public interface RequestStringHandler {
    List<Sort.Order> parseSortingCondition(String sortCondition);
}
