package mate.academy.springboot.swagger.service.impl;

import lombok.RequiredArgsConstructor;
import mate.academy.springboot.swagger.exception.DataProcessingException;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.repository.ProductRepository;
import mate.academy.springboot.swagger.service.ProductService;
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
}
