package mate.academy.springboot.swagger.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
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
    public Product getById(Long id) {
        return productRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Can't find product by id " + id));
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteAll(List.of(productRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Can't delete product by id " + id))));
    }

    @Override
    public void update(Long id, Product product) {
        Product prevProduct = productRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Can't update product by id " + id));
        prevProduct.setPrice(product.getPrice());
        prevProduct.setTitle(product.getTitle());
        productRepository.save(prevProduct);
    }

    @Override
    public List<Product> findAll(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest).toList();
    }

    @Override
    public List<Product> findAllByPriceBetween(BigDecimal from, BigDecimal to,
                                               PageRequest pageRequest) {
        return productRepository.findAllByPriceBetween(from, to, pageRequest);
    }
}
