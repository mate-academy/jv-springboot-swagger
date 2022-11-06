package mate.academy.springboot.swagger.service;

import java.util.List;
import org.springframework.data.domain.Sort;

public interface ProductSortService {

    List<Sort.Order> sortBy(String sort);
}
