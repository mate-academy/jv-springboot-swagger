package mate.academy.springboot.swagger.service;

import org.springframework.data.domain.Sort;

import java.util.List;

public interface SortService {
    List<Sort.Order> parseSortingCondition(String sortCondition);
}
