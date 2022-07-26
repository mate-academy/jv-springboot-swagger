package mate.academy.springboot.swagger.service;

import java.math.BigDecimal;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Product create(Product product) {
        return repository.save(product);
    }

    @Override
    public Product getById(Long id) {
        return repository.getById(id);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Product update(Product product, Long id) {
        product.setId(id);
        return repository.save(product);
    }

    @Override
    public Page<Product> findAll(PageRequest req) {
        return repository.findAll(req);
    }

    @Override
    public Page<Product> findAllByPriceBetween(BigDecimal from, BigDecimal to, PageRequest req) {
        return repository.findAllByPriceBetween(from, to, req);
    }
}
