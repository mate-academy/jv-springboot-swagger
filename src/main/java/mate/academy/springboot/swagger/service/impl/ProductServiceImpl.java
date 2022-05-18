package mate.academy.springboot.swagger.service.impl;

import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.springboot.swagger.exception.DataProcessingException;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.repository.ProductRepository;
import mate.academy.springboot.swagger.service.ProductService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(
                () -> new DataProcessingException("Product with id " + id + " not found"));
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
    public List<Product> findAll(BigDecimal priceFrom, BigDecimal priceTo,
                                 PageRequest pageRequest) {
        if (priceFrom == null) {
            priceFrom = BigDecimal.ZERO;
        }
        if (priceTo == null) {
            priceTo = BigDecimal.valueOf(Double.MAX_VALUE);
        }
        return productRepository.findAll(priceFrom, priceTo, pageRequest);
    }
}
