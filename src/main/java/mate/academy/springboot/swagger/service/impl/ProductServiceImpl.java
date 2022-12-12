package mate.academy.springboot.swagger.service.impl;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.repository.ProductRepository;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.ProductSortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductSortService productSortService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,
                              ProductSortService productSortService) {
        this.productRepository = productRepository;
        this.productSortService = productSortService;
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product getById(Long id) {
        return productRepository.getById(id);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> findAll(PageRequest pageRequest, String sortBy) {
        return productRepository.findAll(pageRequest
                .withSort(productSortService.getSort(sortBy))).toList();
    }

    @Override
    public List<Product> findAllByPriceBetween(BigDecimal from, BigDecimal to,
                                               PageRequest pageRequest, String sortBy) {
        return productRepository.findAllByPriceBetween(from, to,
                pageRequest.withSort(productSortService.getSort(sortBy)), sortBy);
    }
}
