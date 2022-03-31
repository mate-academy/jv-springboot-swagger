package mate.academy.springboot.swagger.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.repository.ProductRepository;
import mate.academy.springboot.swagger.service.ProductService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product getById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product with id" + id + " not found."));
    }

    @Override
    public void delete(Long id) {
        try {
            productRepository.deleteById(id);
        } catch (RuntimeException e) {
            throw new RuntimeException("Product with id" + id + " not found.");
        }

    }

    @Override
    public List<Product> getAllByPrice(BigDecimal from, BigDecimal to, Pageable pageable) {
        if (Objects.isNull(from)) {
            from = productRepository.getMinPrice();
        }
        if (Objects.isNull(to)) {
            to = productRepository.getMaxPrice();
        }
        return productRepository.findByPrice(from, to, pageable).toList();
    }

    @Override
    public List<Product> getAll(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest).toList();
    }
}
