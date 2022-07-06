package mate.academy.springboot.swagger.service.impl;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.repository.ProductRepository;
import mate.academy.springboot.swagger.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository repository;

    @Autowired
    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Product create(Product product) {
        return repository.save(product);
    }

    @Override
    public Product get(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Product update(Product updatedProduct) {
        return repository.save(updatedProduct);
    }

    @Override
    public List<Product> getAll(PageRequest pageRequest) {
        return repository.findAll(pageRequest).toList();
    }

    public List<Product> getAll(BigDecimal from, BigDecimal to, PageRequest pageRequest) {
        return repository.findAllByPriceBetween(from, to, pageRequest);
    }
}
