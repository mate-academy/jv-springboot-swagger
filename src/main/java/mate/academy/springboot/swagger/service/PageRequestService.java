package mate.academy.springboot.swagger.service;

import org.springframework.data.domain.PageRequest;

public interface PageRequestService {
    PageRequest getPageRequest(Integer count, Integer page, String sortBy);
}
