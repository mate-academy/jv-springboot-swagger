package mate.academy.springboot.swagger.sorter;

import java.util.List;
import org.springframework.data.domain.Sort;

public interface MyCustomSorter {
    List<Sort.Order> getSortingOrders(String sortBy);
}
