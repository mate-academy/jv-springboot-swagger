package mate.academy.springboot.swagger.service.impl;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.repository.ProductRepository;
import mate.academy.springboot.swagger.service.ProductService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class ProductServiceImpl implements ProductService {
    private ProductRepository repository;

    @Override
    public Product save(Product product) {
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
    public void update(Product product) {
        repository.update(product.getTitle(), product.getPrice(), product.getId());
    }

    @Override
    public List<Product> getAll(PageRequest pageRequest) {
        return repository.findAll(pageRequest).getContent();
    }

    @Override
    public List<Product> findAllByPriceBetween(BigDecimal from, BigDecimal to, Pageable pageable) {
        return repository.findAllByPriceBetween(from, to, pageable);
    }
}
