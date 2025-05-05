package mate.academy.springboot.swagger.service;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.repository.ProductRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product getById(Long id) {
        return productRepository.getReferenceById(id);
    }

    @Override
    public Product update(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllWherePriceBetween(BigDecimal priceMin, BigDecimal priceMax,
            PageRequest pageRequest) {
        if (priceMin.compareTo(priceMax) == 1) {
            BigDecimal buf = priceMin;
            priceMin = priceMax;
            priceMax = buf;
        }
        return productRepository.findAllByPriceBetween(priceMin, priceMax, pageRequest);
    }

    @Override
    public List<Product> getAll(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest).toList();
    }
}
