package mate.academy.springboot.service.impl;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.model.Product;
import mate.academy.springboot.repository.ProductRepository;
import mate.academy.springboot.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Autowired
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
                new RuntimeException("Can't find product by id " + id));
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> findAll(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest).toList();
    }

    @Override
    public List<Product> findAllByPriceBetween(BigDecimal from,
                                               BigDecimal to,
                                               PageRequest pageRequest) {
        return productRepository.findAllByPriceBetween(from, to, pageRequest);
    }
}
