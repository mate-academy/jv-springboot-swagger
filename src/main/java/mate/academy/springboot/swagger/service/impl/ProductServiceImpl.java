package mate.academy.springboot.swagger.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.AllArgsConstructor;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.repository.ProductRepository;
import mate.academy.springboot.swagger.service.ProductService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product get(Long id) {
        return productRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Can`t find product by id: " + id));
    }

    @Override
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new NoSuchElementException("Can`t find product by id: " + id);
        }
        productRepository.deleteById(id);
    }

    @Override
    public Product update(Product product) {
        if (!productRepository.existsById(product.getId())) {
            throw new NoSuchElementException("Can`t find " + product);
        }
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAll(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest).toList();
    }

    @Override
    public List<Product> getAllByPriceBetween(BigDecimal from,
                                              BigDecimal to,
                                              PageRequest pageRequest) {
        return productRepository.findProductsByPriceBetween(from, to, pageRequest).toList();
    }

    @Override
    public Sort parseSortParam(String param) {
        List<Sort.Order> orders = new ArrayList<>();
        if (param.contains(":")) {
            String[] sortingFields = param.split(";");
            for (String field : sortingFields) {
                Sort.Order order;
                if (field.contains(":")) {
                    String[] fieldAndDirection = field.split(":");
                    order = new Sort.Order(Sort.Direction.valueOf(fieldAndDirection[1]),
                            fieldAndDirection[0]);
                } else {
                    order = new Sort.Order(Sort.Direction.DESC, field);
                }
                orders.add(order);
            }
        } else {
            orders.add(new Sort.Order(Sort.Direction.DESC, param));
        }
        return Sort.by(orders);
    }
}
