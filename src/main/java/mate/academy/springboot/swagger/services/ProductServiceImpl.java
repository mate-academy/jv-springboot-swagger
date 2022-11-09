package mate.academy.springboot.swagger.services;

import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.repo.ProductRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public Product crete(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product getById(Long id) {
        try {
            return productRepository.getById(id);
        } catch (Exception e) {
            throw new RuntimeException("Can`t get product by id: " + id);
        }

    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> getAll(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest).toList();
    }

    @Override
    public List<Product> getAllProductWherePriceBetween(
            BigDecimal from, BigDecimal to, PageRequest pageRequest) {
        return productRepository.getAllByPriceBetween(from, to, pageRequest).toList();
    }
}
