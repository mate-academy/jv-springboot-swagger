package mate.academy.springboot.swagger.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import lombok.AllArgsConstructor;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.repository.ProductRepository;
import mate.academy.springboot.swagger.service.ProductService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;

    @Override
    public Product save(Product entity) {
        return productRepository.save(entity);
    }

    @Override
    public Product find(Long id) {
        return productRepository.findById(id).orElseThrow(()
                -> new NoSuchElementException("No such product by id: "
                + id + " exists"));
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public void update(Product entity) {
        productRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
