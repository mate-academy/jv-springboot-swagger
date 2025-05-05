package mate.academy.springboot.swagger.service.impl;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.repository.ProductRepository;
import mate.academy.springboot.swagger.service.ProductService;
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
    public void delete(Long id) {
        repository.delete(repository.getById(id));
    }

    @Override
    public Product update(Product product) {
        return repository.save(product);
    }

    @Override
    public List<Product> findAll(PageRequest pageRequest) {
        return repository.findAll(pageRequest).getContent();
    }

    @Override
    public List<Product> findAllByPriceBetween(PageRequest pageRequest, BigDecimal from,
                                               BigDecimal to) {
        return repository.getProductsByPriceBetween(pageRequest, from, to);
    }
}
