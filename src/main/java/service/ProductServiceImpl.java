package service;

import java.math.BigDecimal;
import java.util.List;
import model.Product;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import repository.ProductRepository;

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
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product getById(Long id) {
        return productRepository.getById(id);
    }

    @Override
    public List<Product> findAllByPriceBetween(BigDecimal from,
                                               BigDecimal to,
                                               PageRequest pageRequest) {
        return productRepository.getAllByPriceBetween(from, to, pageRequest);
    }

    @Override
    public List<Product> findAll(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest).toList();
    }
}
