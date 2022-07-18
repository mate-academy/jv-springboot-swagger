package mate.academy.springboot.swagger.service.impl;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.repository.ProductRepository;
import mate.academy.springboot.swagger.service.ProductService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Product save(Product product) {
        return repository.save(product);
    }

    @Override
    public Product get(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public void remove(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Product update(Product product) {
        return repository.save(product);
    }

    @Override
    public List<Product> findAll(Pageable pageable) {
        return repository.findAll(pageable).toList();
    }

    @Override
    public List<Product> findAll(BigDecimal from, BigDecimal to, Pageable pageable) {
        return repository.findAllByPriceBetween(from, to, pageable);
    }
}
