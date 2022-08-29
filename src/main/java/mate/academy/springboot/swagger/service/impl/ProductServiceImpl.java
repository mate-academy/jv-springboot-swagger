package mate.academy.springboot.swagger.service.impl;

import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.repository.ProductRepository;
import mate.academy.springboot.swagger.service.ProductService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Product with id " + id + "is not exist at DB"));
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public void update(Product product) {
        productRepository.update(
                product.getTitle(),
                product.getPrice(),
                product.getId());
    }

    @Override
    public List<Product> getProductsByPriceBetween(BigDecimal from,
                                                   BigDecimal to,
                                                   PageRequest pageRequest) {
        return productRepository.getProductByPriceBetween(from, to, pageRequest);
    }

    @Override
    public List<Product> findAll(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest).toList();
    }
}
