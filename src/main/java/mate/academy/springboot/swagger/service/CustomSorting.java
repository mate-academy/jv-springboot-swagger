package mate.academy.springboot.swagger.service;

import org.springframework.data.domain.Sort;

public interface CustomSorting {
    Sort sortBy(String sort);
}
