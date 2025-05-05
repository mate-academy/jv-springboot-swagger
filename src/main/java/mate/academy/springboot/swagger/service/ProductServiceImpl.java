package mate.academy.springboot.swagger.service;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.repository.ProductRepository;
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
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product getById(Long id) {
        return productRepository.getProductById(id).orElseThrow(
                () -> new RuntimeException("Can't get product with id " + id));
    }

    @Override
    public void delete(Long id) {
        productRepository.delete(productRepository.getProductById(id).orElseThrow(
                () -> new RuntimeException("Can't delete product with id " + id)));
    }

    @Override
    public Product update(Product product) {
        return productRepository.saveAndFlush(product);
    }

    @Override
    public List<Product> findAllByPriceBetween(BigDecimal from, BigDecimal to, Pageable pageable) {
        return productRepository.findAllByPriceBetween(from, to, pageable);
    }

    @Override
    public List<Product> findAll(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest).toList();
    }
}
