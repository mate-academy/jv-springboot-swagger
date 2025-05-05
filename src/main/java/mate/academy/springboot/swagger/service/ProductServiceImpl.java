package mate.academy.springboot.swagger.service;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.repository.ProductRepository;
import mate.academy.springboot.swagger.service.util.SortParser;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final SortParser sortParser;

    public ProductServiceImpl(ProductRepository productRepository, SortParser sortParser) {
        this.productRepository = productRepository;
        this.sortParser = sortParser;
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
        return productRepository.findAll(pageRequest.withSort(sortParser.getSort(sortBy))).toList();
    }

    @Override
    public List<Product> findAllByPriceBetween(BigDecimal firstPrice, BigDecimal secondPrice,
                                               PageRequest pageRequest, String sortBy) {
        return productRepository.findAllByPriceBetween(firstPrice, secondPrice,
                pageRequest.withSort(sortParser.getSort(sortBy)));
    }
}
