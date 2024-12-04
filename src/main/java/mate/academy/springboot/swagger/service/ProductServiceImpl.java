package mate.academy.springboot.swagger.service;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product getById(Long id) {
        return productRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Can't find product with id: " + id)
        );
    }

    @Override
    public Product update(Long id, Product product) {
        if (productRepository.findById(id).isEmpty()) {
            throw new RuntimeException("Can't update product with id: " + id);
        }
        product.setId(id);
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAll(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest).toList();
    }

    @Override
    public List<Product> getAllByPriceBetween(BigDecimal from, BigDecimal to,
                                              PageRequest pageRequest) {
        return productRepository.findAllByPriceIsBetween(from, to, pageRequest);
    }
}
