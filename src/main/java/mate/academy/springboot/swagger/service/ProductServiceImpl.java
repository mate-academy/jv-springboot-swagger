package mate.academy.springboot.swagger.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.repository.ProductRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product get(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product
                .orElseThrow(() -> new NoSuchElementException("Product with id "
                        + id + " not found"));
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product update(Product product) {
        Product retrievedProduct = productRepository.getById(product.getId());
        retrievedProduct.setTitle(product.getTitle());
        retrievedProduct.setPrice(product.getPrice());
        return productRepository.save(retrievedProduct);
    }

    @Override
    public List<Product> findAll(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest).toList();
    }

    @Override
    public List<Product> findAllByPriceBetween(BigDecimal from,
                                               BigDecimal to,
                                               PageRequest pageRequest) {
        return productRepository.findAllByPriceBetween(from, to);
    }
}
