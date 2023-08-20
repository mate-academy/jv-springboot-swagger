package mate.academy.springboot.swagger.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import mate.academy.springboot.swagger.dao.ProductRepository;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product getById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Product not found with id: " + id));
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product update(Product product, Long id) {
        Product oldProduct = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Product not found with this "
                        + "id: " + id));
        oldProduct.setTitle(product.getTitle());
        oldProduct.setPrice(product.getPrice());
        return productRepository.save(oldProduct);
    }

    @Override
    public List<Product> getAllByPriceBetween(BigDecimal from, BigDecimal to, PageRequest req) {
        return productRepository.findAllByPriceBetween(from, to, req);
    }

    @Override
    public List<Product> getAll(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest).toList();
    }
}
