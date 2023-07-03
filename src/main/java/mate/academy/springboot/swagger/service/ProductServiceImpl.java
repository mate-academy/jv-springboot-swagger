package mate.academy.springboot.swagger.service;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.repository.ProductRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Product createOrUpdate(Product product) {
        return repository.save(product);
    }

    @Override
    public Product getById(Long id) {
        return repository.getById(id);
    }

    @Override
    public void delete(Long id) {
        repository.delete(getById(id));
    }

    @Override
    public List<Product> findAll(Pageable pagination) {
        return repository.findAll(pagination).toList();
    }

    @Override
    public List<Product> findAllBetweenPrice(
            BigDecimal priceFrom, BigDecimal priceTo, Pageable pagination
    ) {
        return repository.findByPriceBetween(priceFrom, priceTo, pagination);
    }
}
