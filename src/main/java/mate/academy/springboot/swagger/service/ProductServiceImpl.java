package mate.academy.springboot.swagger.service;

import java.util.List;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.repository.ProductRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public Product get(Long id) {
        return productRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("No product found with id " + id));
    }

    @Override
    public Product add(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product update(Long id, Product product) {
        productRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("No product to update with id " + id));
        product.setId(id);
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllByPriceBetween(Long from, Long to, PageRequest pageRequest) {
        return productRepository.findAllByPriceBetween(from, to, pageRequest);
    }

    @Override
    public List<Product> getAll(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest).toList();
    }
}
