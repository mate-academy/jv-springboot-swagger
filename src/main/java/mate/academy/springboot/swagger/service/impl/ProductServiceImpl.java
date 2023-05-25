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
                new RuntimeException("Can't get Product from DB by ID: " + id));
    }

    @Override
    public boolean delete(Long id) {
        return productRepository.deleteProductById(id);
    }

    @Override
    public Product update(Product product) {
        try {
            findById(product.getId());
            return productRepository.save(product);
        } catch (RuntimeException e) {
            throw new RuntimeException("Can't update Product in DB!" + product);
        }
    }

    @Override
    public List<Product> findAll(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest).toList();
    }

    @Override
    public List<Product> findAllByPriceBetween(BigDecimal from,
                                               BigDecimal to,
                                               PageRequest pageRequest) {
        return productRepository.getProductsByPriceBetween(from, to, pageRequest);
    }
}
