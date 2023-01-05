package mate.academy.springboot.swagger.service.impl;

import java.util.List;
import javax.persistence.EntityNotFoundException;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.repository.ProductRepository;
import mate.academy.springboot.swagger.service.ProductService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product get(Long id) {
        return productRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Couldn't find product by id: " + id));
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product update(Product product) {
        Product productFromDb = get(product.getId());
        productFromDb.setPrice(product.getPrice());
        productFromDb.setTitle(product.getTitle());
        return productRepository.save(productFromDb);
    }

    @Override
    public List<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable)
                .toList();
    }

    @Override
    public List<Product> findProductsByPriceBetween(Integer from, Integer to, Pageable pageable) {
        return productRepository.findProductsByPriceBetween(from, to, pageable);
    }
}
