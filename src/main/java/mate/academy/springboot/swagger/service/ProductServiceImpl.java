package mate.academy.springboot.swagger.service;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.repository.ProductRepository;
import mate.academy.springboot.swagger.util.PageRequestUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Cant find product with ID: " + id));
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product update(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> findAll(Integer page, Integer count, String sortBy) {
        Pageable sortParameters = PageRequestUtil.getSortParameters(page, count, sortBy);
        return productRepository.findAll(sortParameters).toList();
    }

    @Override
    public List<Product> findAllByPriceBetween(BigDecimal from, BigDecimal to,
                                               Integer page, Integer count, String sortBy) {
        Pageable sortParameters = PageRequestUtil.getSortParameters(page, count, sortBy);
        return productRepository.findAllByPriceBetween(from, to, sortParameters);
    }
}
