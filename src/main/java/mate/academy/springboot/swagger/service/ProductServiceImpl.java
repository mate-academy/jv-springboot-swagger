package mate.academy.springboot.swagger.service;

import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.repository.ProductRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;

    @Override
    public Product save(Product product) {
        return repository.save(product);
    }

    @Override
    public Product get(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Can't find product by id " + id));
    }

    @Override
    public List<Product> getAll(PageRequest request) {
        return repository.findAll();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Product> getAllByPriceBetween(Long from, Long to, PageRequest pageRequest) {
        return repository.findAllByPriceBetween(from, to, pageRequest);
    }
}
