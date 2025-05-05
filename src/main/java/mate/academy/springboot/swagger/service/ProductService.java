package mate.academy.springboot.swagger.service;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getById(Long id) {
        return productRepository.getById(id);
    }

    public Product add(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getAllByPriceBetween(BigDecimal from,
                                              BigDecimal to,
                                              PageRequest pageRequest) {
        return productRepository.getAllByPriceBetween(from, to, pageRequest);
    }

    public Page<Product> getAll(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest);
    }
}
