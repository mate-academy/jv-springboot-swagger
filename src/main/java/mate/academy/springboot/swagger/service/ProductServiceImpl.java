package mate.academy.springboot.swagger.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.repostitory.ProductRepository;
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
        return productRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Not such product by id=" + id));
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> getAllWithPriceBetween(BigDecimal from,
                                                BigDecimal to,
                                                Pageable pageable) {
        return productRepository.findAllByPriceBetween(from, to, pageable);
    }

    @Override
    public List<Product> findAll(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest).toList();
    }
}
