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

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;

    @Override
    public Product add(Product product) {
        return repository.save(product);
    }

    @Override
    public Product getById(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Can`t find product with id: " + id));
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Product update(Product product, Long id) {
        Product productFromDb = this.getById(id);
        productFromDb.setTitle(product.getTitle());
        productFromDb.setPrice(product.getPrice());
        return repository.save(productFromDb);
    }

    @Override
    public List<Product> getAll(PageRequest pageRequest) {
        return repository.findAll(pageRequest).toList();
    }

    @Override
    public List<Product> getAllByPriceBetween(BigDecimal from, BigDecimal to,
            PageRequest pageRequest) {
        return repository.findAllByPriceBetween(from, to, pageRequest);
    }
}
