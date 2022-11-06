package mate.academy.springboot.swagger.service;

import org.springframework.data.domain.Sort;
import java.util.List;

public interface ProductSortService {

    List<Sort.Order> sortBy(String sort);
}
