package mate.academy.springboot.swagger.service.impl;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.repository.ProductRepository;
import mate.academy.springboot.swagger.service.ProductService;
import org.springframework.data.domain.PageRequest;
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
    public Product findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Can't get a product by id " + id));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Product> findAll(PageRequest pageRequest) {
        return repository.findAll(pageRequest).toList();
    }

    @Override
    public List<Product> findAllByPriceBetween(BigDecimal fromPrice, BigDecimal toPrice,
                                               Pageable pageable) {
        return repository.findAllByPriceBetween(fromPrice, toPrice, pageable);
    }
}
