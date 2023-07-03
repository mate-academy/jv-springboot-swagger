package mate.academy.springboot.swagger.service.impl;

import java.math.BigDecimal;
import javax.persistence.EntityNotFoundException;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.persistance.ProductRepository;
import mate.academy.springboot.swagger.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product getById(Long id) {
        return productRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(
                        "Product not found by id: " + id
                )
        );
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product update(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Page<Product> getAll(Pageable pageRequest) {
        return productRepository.findAll(pageRequest);
    }

    @Override
    public Page<Product> getByPriceBetween(BigDecimal min, BigDecimal max,
                                           Pageable pageRequest) {
        return productRepository.findAllByPriceBetween(min, max, pageRequest);
    }
}
