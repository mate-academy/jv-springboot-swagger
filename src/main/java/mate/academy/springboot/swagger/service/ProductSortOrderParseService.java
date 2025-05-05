package mate.academy.springboot.swagger.service;

import java.util.List;
import org.springframework.data.domain.Sort;

public interface ProductSortOrderParseService {
    List<Sort.Order> parse(String sortBy);
}
