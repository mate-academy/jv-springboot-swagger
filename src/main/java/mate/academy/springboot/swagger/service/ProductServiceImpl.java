package mate.academy.springboot.swagger.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void injection() {
        Random random = new Random();
        List<Product> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Product product = new Product();
            product.setTitle("iPhone" + random.nextInt(100));
            product.setPrice(new BigDecimal(random.nextInt(1000)));
            list.add(product);
        }
        productRepository.saveAll(list);
    }

    @Override
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product get(Long id) {
        return productRepository.getById(id);
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public void update(Product product) {
        productRepository.save(product);
    }

    @Override
    public List<Product> findAll(Integer page, Integer size, String sortBy) {
        return productRepository.findAll(PaginationUtil.sortByParams(page, size, sortBy)).toList();
    }

    @Override
    public List<Product> findAllByPriceBetween(BigDecimal from, BigDecimal to,
                                               Integer page, Integer size, String sortBy) {
        return productRepository.findAllByPriceBetween(from, to,
                PaginationUtil.sortByParams(page, size, sortBy));
    }
}
