package mate.academy.springboot.swagger.service.impl;

import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.repository.ProductRepository;
import mate.academy.springboot.swagger.service.ProductService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;

    @Override
    public Product save(Product product) {
        return repository.save(product);
    }

    @Override
    public Product getById(Long id) {
        return repository.getById(id);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Product> getAllByPrice(BigDecimal from, BigDecimal to, PageRequest pageRequest) {
        return repository.getAllByPriceBetween(from,to,pageRequest);
    }

    @Override
    public List<Product> getAll(PageRequest pageRequest) {
        return repository.findAll(pageRequest).toList();
    }
}
