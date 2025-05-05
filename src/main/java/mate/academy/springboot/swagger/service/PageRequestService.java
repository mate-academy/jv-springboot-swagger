package mate.academy.springboot.swagger.service;

import org.springframework.data.domain.PageRequest;

public interface PageRequestService {
    PageRequest getPageRequest(Integer page, Integer size, String sortBy);
}
