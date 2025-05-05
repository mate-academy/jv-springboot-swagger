package mate.academy.springboot.swagger.service;

import static mate.academy.springboot.swagger.util.Sorting.getSortFromRequestParam;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.repository.ProductRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findAll(Integer page, Integer count, String sortBy) {
        PageRequest pageRequest = PageRequest.of(page, count, getSortFromRequestParam(sortBy));
        return productRepository.findAll(pageRequest).toList();
    }

    @Override
    public List<Product> findAllByPriceBetween(BigDecimal from, BigDecimal to,
                                               Integer page, Integer count,
                                               String sortBy) {
        PageRequest pageRequest = PageRequest.of(page, count, getSortFromRequestParam(sortBy));
        return productRepository.findAllByPriceBetween(from, to, pageRequest);
    }

    @Override
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product getById(Long id) {
        return productRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Product by id " + id + " not found")
        );
    }

    @Override
    public Product update(Product product) {
        Product productForUpdate = productRepository.getById(product.getId());
        productForUpdate.setTitle(product.getTitle());
        productForUpdate.setPrice(product.getPrice());
        return productRepository.save(productForUpdate);
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
