package mate.academy.springboot.swagger.service;

import org.springframework.data.domain.Pageable;

public interface PageableProvider {
    Pageable get(Integer count, Integer page, String sortBy);
}
