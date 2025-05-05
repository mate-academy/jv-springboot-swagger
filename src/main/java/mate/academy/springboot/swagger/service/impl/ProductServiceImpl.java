package mate.academy.springboot.swagger.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.AllArgsConstructor;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.repository.ProductRepository;
import mate.academy.springboot.swagger.service.ProductService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public List<Product> findAll(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest).toList();
    }

    @Override
    public List<Product> findAllByPriceBetween(BigDecimal from, BigDecimal to, Pageable pageable) {
        return productRepository.findAllByPriceBetween(from, to, pageable);
    }

    @Override
    public void update(Product entity) {
        if (!productRepository.existsById(entity.getId())) {
            throw new NoSuchElementException("can't update non "
                    + "existent product by id: " + entity.getId());
        }
        productRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
