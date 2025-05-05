package mate.academy.springboot.swagger.service.util;

import org.springframework.data.domain.PageRequest;

public interface PageRequestService {
    PageRequest getPageRequest(int page, int count, String property);
}
