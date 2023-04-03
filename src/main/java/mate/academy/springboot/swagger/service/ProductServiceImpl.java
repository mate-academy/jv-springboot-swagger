package mate.academy.springboot.swagger.service;

import lombok.RequiredArgsConstructor;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.repository.ProductRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


@Component
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product getById(Long id) {
        return productRepository.findById(id).orElseThrow(()
                -> new NoSuchElementException("Can`t find product by id " + id));
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product update(Product product) {
        return productRepository.saveAndFlush(product);
    }

    @Override
    public List<Product> findAllProductWithParam(Integer count, Integer page, String sortBy) {
        return productRepository.findAll(createPageRequest(count, page, sortBy)).toList();
    }

    @Override
    public List<Product> findAllByPriseBetween(BigDecimal from, BigDecimal to,
                                               Integer count, Integer page, String sortBy) {
        return productRepository.getAllByPriseBetween(from, to, createPageRequest(count, page, sortBy));
    }

    private Pageable createPageRequest(Integer count, Integer page, String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if(sortBy.contains(":")) {
            String[] scoringFilter = sortBy.split(";");
            for(String field : scoringFilter) {
                Sort.Order order;
                if (field.contains(":")) {
                    String[] fieldsAndDirections = field.split(":");
                    order = new Sort.Order(Sort.Direction.valueOf(fieldsAndDirections[1]),
                            fieldsAndDirections[0]);
                } else {
                    order = new Sort.Order(Sort.Direction.DESC, field);
                }
                orders.add(order);
            }
        } else {
            Sort.Order order = new Sort.Order(Sort.Direction.DESC, sortBy);
            orders.add(order);
        }
        Sort sort = Sort.by(orders);
        return PageRequest.of(page, count, sort);
    }
}
