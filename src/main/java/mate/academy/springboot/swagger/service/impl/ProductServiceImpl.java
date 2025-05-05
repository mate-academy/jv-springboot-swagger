package mate.academy.springboot.swagger.service.impl;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.dao.ProductDao;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductDao productDao;

    public ProductServiceImpl(ProductDao productRepository) {
        this.productDao = productRepository;
    }

    @Override
    public Product add(Product product) {
        return productDao.save(product);
    }

    @Override
    public Product getById(Long id) {
        return productDao.getById(id);
    }

    @Override
    public void remove(Long id) {
        productDao.deleteById(id);
    }

    @Override
    public List<Product> getAllProducts(PageRequest pageRequest) {
        return productDao.findAll(pageRequest).toList();
    }

    @Override
    public List<Product> getAllProductsByPriceBetween(BigDecimal from,
                                                        BigDecimal to,
                                                        PageRequest pageRequest) {
        return productDao.getAllByPriceBetween(from, to, pageRequest);
    }
}
