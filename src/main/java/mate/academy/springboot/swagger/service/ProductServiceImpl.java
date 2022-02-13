package mate.academy.springboot.swagger.service;

import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.repository.ProductRepository;
import mate.academy.springboot.swagger.util.PaginationUtil;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repo;

    @Override
    public Product save(Product product) {
        return repo.save(product);
    }

    @Override
    public Product getById(Long id) {
        return repo.getById(id);
    }

    @Override
    public void update(Product product) {
        repo.save(product);
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    @Override
    public List<Product> findAll() {
        return repo.findAll();
    }

    @Override
    public List<Product> findAll(Integer page, Integer size, String sortBy) {
        return repo.findAll(PaginationUtil.sortByParams(page, size, sortBy))
                .toList();
    }

    @Override
    public List<Product> findAllByPriceBetween(BigDecimal from, BigDecimal to,
                                               Integer page, Integer size, String sortBy) {
        return repo.findAllByPriceBetween(from, to,
                PaginationUtil.sortByParams(page, size, sortBy));
    }
}
