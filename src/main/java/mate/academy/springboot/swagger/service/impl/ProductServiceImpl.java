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
    private ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Product save(Product product) {
        return repository.save(product);
    }

    @Override
    public Product getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Didn't find product with id " + id));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Product> getAll(PageRequest pageRequest) {
        return repository.findAll(pageRequest).toList();
    }

    @Override
    public List<Product> findAllByPriceBetween(BigDecimal priceFrom, BigDecimal priceTo,
                                               Pageable pageable) {
        return repository.findAllByPriceBetween(priceFrom, priceTo,pageable).toList();
    }
}
