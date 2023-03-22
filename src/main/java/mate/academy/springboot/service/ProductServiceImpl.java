package mate.academy.springboot.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import mate.academy.springboot.ProductRepository;
import mate.academy.springboot.model.Product;
import org.springframework.data.domain.PageRequest;
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
                () -> new NoSuchElementException("Can`t find product by id " + id)
        );
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> findAllProducts(PageRequest pageRequest) {
        return productRepository.findAll();
    }

    @Override
    public List<Product> findAllByProductsPriceBetween(BigDecimal from, BigDecimal to,
                                                       PageRequest pageRequest) {
        return productRepository.findAllByProductsPriceBetween(from, to, pageRequest);
    }
}
