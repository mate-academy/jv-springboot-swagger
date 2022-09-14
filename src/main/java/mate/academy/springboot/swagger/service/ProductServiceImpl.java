package mate.academy.springboot.swagger.service;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.repository.ProductRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Product add(Product product) {
        return repository.save(product);
    }

    @Override
    public Product get(Long id) {
        return repository.getById(id);
    }

    @Override
    public void update(Product product) {
        repository.save(product);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Product> findAllByPriceBetween(BigDecimal from, BigDecimal to, Pageable pageable) {
        return repository.findAllByPriceBetween(from, to, pageable);
    }

    @Override
    public List<Product> findAll(Pageable pageable) {
        return repository.findAll(pageable).toList();
    }
}
