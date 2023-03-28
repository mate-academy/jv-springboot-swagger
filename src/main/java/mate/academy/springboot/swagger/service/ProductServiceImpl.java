package mate.academy.springboot.swagger.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
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
    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Can't find product by id: " + id));
    }

    @Override
    public List<Product> findAll(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest).getContent();
    }

    @Override
    public List<Product> findAllByPriceBetween(Pageable pageable,
                                                 BigDecimal from,
                                                 BigDecimal to) {
        return productRepository.findAllByPriceBetween(pageable, from, to);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
