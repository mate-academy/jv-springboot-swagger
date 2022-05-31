package mate.academy.springboot.swagger.service;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.repository.ProductRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImp implements ProductService {
    private final ProductRepository repository;

    @Override
    public Product save(Product product) {
        return repository.save(product);
    }

    @Override
    public Product getById(Long id) throws EntityNotFoundException {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(
            String.format("Product with id %d is not found", id)));
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Product> findAllSortedByPriceOrTitle(PageRequest pageRequest) {
        return repository.findAllSortedByPriceOrTitle(pageRequest);
    }

    @Override
    public List<Product> findAllByPriceBetweenSortedByPriceOrTitle(
            BigDecimal from, BigDecimal to, PageRequest pageRequest) {
        return repository.findAllByPriceBetweenSortedByPriceOrTitle(from, to, pageRequest);
    }
}
