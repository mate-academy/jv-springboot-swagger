package mate.academy.springboot.swagger.service.impl;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.exception.DataProcessingException;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.repository.ProductRepository;
import mate.academy.springboot.swagger.service.ProductService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product add(Product product) {
        return productRepository.save(product);
    }

    public Product findById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new DataProcessingException(
                        "Product for id: %d not found".formatted(productId)));
    }

    public void deleteById(Long productId) {
        productRepository.deleteById(productId);
    }

    public Product update(Product product) {
        productRepository.findById(product.getId()).orElseThrow(() -> new DataProcessingException(
                        "Couldn't find original product to update, consider creating a new one."));
        return productRepository.save(product);
    }

    @Override
    public List<Product> findAll(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest).toList();
    }

    @Override
    public List<Product> findByPriceBetween(BigDecimal priceFrom,
                                            BigDecimal priceTo,
                                            PageRequest pageRequest) {
        return productRepository.findAllByPriceBetween(
                priceFrom,
                priceTo,
                pageRequest);
    }
}
