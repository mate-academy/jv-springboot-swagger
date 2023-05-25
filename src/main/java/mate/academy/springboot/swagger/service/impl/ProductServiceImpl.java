package mate.academy.springboot.swagger.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.repository.ProductRepository;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.specification.SpecificationManager;
import mate.academy.springboot.swagger.service.util.SortUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    private static final String SORT_PARAM = "sortBy";
    private static final String SIZE_PARAM = "size";
    private static final String PAGE_PARAM = "page";
    private final ProductRepository productRepository;
    private final SpecificationManager<Product> productSpecificationManager;
    private final SortUtil sortUtil;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,
                              SpecificationManager<Product> productSpecificationManager,
                              SortUtil sortUtil) {
        this.productRepository = productRepository;
        this.productSpecificationManager = productSpecificationManager;
        this.sortUtil = sortUtil;
    }

    @Override
    public Product create(Product product) {
        return productRepository.saveAndFlush(product);
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
    public Product update(Product product) {
        return productRepository.saveAndFlush(product);
    }

    @Override
    public Page<Product> getAll(Map<String, String> params,
                                Integer page, Integer size) {
        List<Sort.Order> orders = new ArrayList<>();
        if (params.containsKey(SORT_PARAM)) {
            orders = sortUtil.getOrders(params.get(SORT_PARAM));
        }
        Sort sort = Sort.by(orders);
        Specification<Product> spec = null;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (!entry.getKey().equals(SORT_PARAM)
                    && !entry.getKey().equals(PAGE_PARAM)
                    && !entry.getKey().equals(SIZE_PARAM)) {
                Specification<Product> sp = productSpecificationManager
                        .getSpecification(entry.getKey(), entry.getValue().split(","));
                spec = spec == null
                        ? Specification.where(sp) : spec.and(sp);
            }
        }
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return productRepository.findAll(spec, pageRequest);
    }

    @Override
    public Page<Product> getAll(Integer page, Integer size, String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        orders = sortUtil.getOrders(sortBy);
        Sort sort = Sort.by(orders);
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return productRepository.findAll(pageRequest);
    }
}
