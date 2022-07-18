package mate.academy.springboot.swagger.service;

import org.springframework.data.domain.Pageable;

public interface PageableSupplier {
    Pageable get(Integer count, Integer page, String sortBy);
}
