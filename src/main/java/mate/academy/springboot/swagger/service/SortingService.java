package mate.academy.springboot.swagger.service;

import org.springframework.data.domain.Sort;

public interface SortingService {
    Sort parse(String params);
}
