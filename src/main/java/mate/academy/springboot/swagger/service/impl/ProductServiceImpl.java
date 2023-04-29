package mate.academy.springboot.swagger.service.impl;

import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.repository.ProductRepository;
import mate.academy.springboot.swagger.repository.specification.SpecificationManager;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.handler.PaginationAndSortingHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final SpecificationManager<Product> specificationManager;
    private final PaginationAndSortingHandler paginationAndSortingHandler;

    @Autowired
    public ProductServiceImpl(ProductRepository repository,
                              SpecificationManager<Product> specificationManager,
                              PaginationAndSortingHandler paginationAndSortingHandler) {
        this.repository = repository;
        this.specificationManager = specificationManager;
        this.paginationAndSortingHandler = paginationAndSortingHandler;
    }

    @Override
    public Product create(Product product) {
        return repository.save(product);
    }

    @Override
    public Product get(Long id) {
        return repository.getById(id);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Product update(Product product) {
        Long id = product.getId();
        if (get(id) == null) {
            throw new NoSuchElementException("No Product with id " + id + " in DataBase");
        }
        return repository.save(product);
    }

    @Override
    public Page<Product> getAll(Map<String, String> params) {
        List<String> ignoreParams = paginationAndSortingHandler.getFields();
        Specification<Product> specification = null;
        for (Map.Entry<String, String> param : params.entrySet()) {
            if (!ignoreParams.contains(param.getKey())) {
                Specification<Product> sp = specificationManager
                        .get(param.getKey(), param.getValue().split(","));
                specification = specification == null ?
                        Specification.where(sp) : specification.and(sp);
            }
        }
        return repository.findAll(specification, paginationAndSortingHandler.handle(params));
    }
}
