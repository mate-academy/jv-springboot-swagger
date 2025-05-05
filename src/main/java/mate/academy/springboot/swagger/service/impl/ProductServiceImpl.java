package mate.academy.springboot.swagger.service.impl;

import java.math.BigDecimal;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.repository.ProductRepository;
import mate.academy.springboot.swagger.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product update(Product product) {
        return productRepository.findById(product.getId())
                .map(productRepository::save)
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                                "Can't find product by id = %s", product.getId())));
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product getById(Long id) {
        return productRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Can't find product by id = %s", id)));
    }

    @Override
    public Page<Product> getAll(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest);
    }

    @Override
    public Page<Product> getAllByPriceBetween(BigDecimal from, BigDecimal to,
                                              PageRequest pageRequest) {
        return productRepository.getProductsByPriceBetween(from, to, pageRequest);
    }
}
