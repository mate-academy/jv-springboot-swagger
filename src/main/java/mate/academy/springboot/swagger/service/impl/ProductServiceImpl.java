package mate.academy.springboot.swagger.service.impl;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.repository.ProductRepository;
import mate.academy.springboot.swagger.service.ProductService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
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
    public Product get(Long id) {
        return productRepository.getById(id);
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public int update(Product product) {
        return productRepository.update(product);
    }

    @Override
    public List<Product> getAll(Integer page, Integer count, String sort) {
        Sort.Order order = sortParser.parseSortCondition(sort);
        PageRequest pageRequest = PageRequest.of(page, count, Sort.by(order));
        return productRepository.findAll(pageRequest).toList();
    }

    @Override
    public List<Product> getAllByPriceBetween(BigDecimal priceFrom, BigDecimal priceTo,
                                              Integer page, Integer count, String sort) {
        Sort.Order order = sortParser.parseSortCondition(sort);
        PageRequest pageRequest = PageRequest.of(page, count, Sort.by(order));
        return productRepository.getAllByPriceBetween(priceFrom, priceTo, pageRequest);
    }
}
