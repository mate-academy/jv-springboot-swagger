package mate.academy.springboot.swagger.service;

import java.util.List;
import org.springframework.data.domain.Sort;

public interface SorterService {
    List<Sort.Order> sort(String sortBy);
}
