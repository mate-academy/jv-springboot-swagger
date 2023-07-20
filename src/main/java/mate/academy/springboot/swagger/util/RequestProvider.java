package mate.academy.springboot.swagger.util;

import org.springframework.data.domain.PageRequest;

public interface RequestProvider {
    PageRequest formPageRequest(Integer count, Integer page, String sortBy);
}
