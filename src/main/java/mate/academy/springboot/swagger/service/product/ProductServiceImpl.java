package mate.academy.springboot.swagger.service.product;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.repository.ProductRepository;
import mate.academy.springboot.swagger.service.util.PageRequestService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final PageRequestService pageRequestService;

    public ProductServiceImpl(ProductRepository productRepository,
                              PageRequestService pageRequestService) {
        this.productRepository = productRepository;
        this.pageRequestService = pageRequestService;
    }

    @Override
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product getById(Long id) {
        return productRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Product by id " + id + " doesn't exist"));
    }

    @Override
    public Product update(Product product) {
        Long id = product.getId();
        return productRepository.findById(id)
                .map(p -> productRepository.save(product)).orElseThrow(
                        () -> new NoSuchElementException("Product by id " + id + " doesn't exist"));
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> findAll(int page, int count, String sortBy) {
        PageRequest pageRequest = pageRequestService.getPageRequest(page, count, sortBy);
        return productRepository.findAll(pageRequest).toList();
    }

    @Override
    public List<Product> findAllByPriceBetween(BigDecimal from, BigDecimal to,
                                               int page, int count, String sortBy) {
        PageRequest pageRequest = pageRequestService.getPageRequest(page, count, sortBy);
        return productRepository.findAllByPriceBetween(from, to, pageRequest).stream().toList();
    }
}
