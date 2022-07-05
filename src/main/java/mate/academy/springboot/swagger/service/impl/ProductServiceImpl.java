package mate.academy.springboot.swagger.service.impl;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.repository.ProductRepository;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.SortService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final SortService sortService;

    public ProductServiceImpl(ProductRepository productRepository,
                              SortService sortService) {
        this.productRepository = productRepository;
        this.sortService = sortService;
    }

    @Override
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product get(Long productId) {
        return productRepository.getById(productId);
    }

    @Override
    public void delete(Long productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public Product update(Product product, Long productId) {
        product.setId(productId);
        return productRepository.save(product);
    }

    @Override
    public List<Product> findAll(Integer count, Integer page, String sortBy) {
        PageRequest pageRequest = sortService.getPageRequest(count, page, sortBy);
        return productRepository.findAll(pageRequest).toList();
    }

    @Override
    public List<Product> findAllBetweenPrice(Integer count, Integer page, String sortBy,
                                             BigDecimal from, BigDecimal to) {
        PageRequest pageRequest = sortService.getPageRequest(count, page, sortBy);
        return productRepository.findAllByPriceBetween(from, to, pageRequest);
    }
}
