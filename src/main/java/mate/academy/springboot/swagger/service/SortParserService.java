package mate.academy.springboot.swagger.service;

import org.springframework.data.domain.Sort;

import java.util.List;

public interface SortParserService {
    List<Sort.Order> orders(String sortBy);
}
