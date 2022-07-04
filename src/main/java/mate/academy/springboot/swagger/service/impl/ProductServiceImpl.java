package mate.academy.springboot.swagger.service.impl;

import java.util.List;
import java.util.Map;
import mate.academy.springboot.swagger.dao.specification.SpecificationManager;
import mate.academy.springboot.swagger.exception.DataProcessException;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.dao.ProductRepository;
import mate.academy.springboot.swagger.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;
    private final SpecificationManager<Product> specificationManager;

    @Autowired
    public ProductServiceImpl(ProductRepository repository,
                              SpecificationManager<Product> specificationManager) {
        this.repository = repository;
        this.specificationManager = specificationManager;
    }

    @Override
    public Product create(Product product) {
        return repository.save(product);
    }

    @Override
    public Product find(Long id) throws DataProcessException {
        return repository.findById(id)
                .orElseThrow(() -> new DataProcessException("Can't find a product by id: " + id + " !"));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Product update(Product product) {
        return repository.save(product);
    }

    @Override
    public List<Product> getAll(Map<String, String> params, Pageable pageable) {
        Specification<Product> specification = null;
        for (Map.Entry<String,String> entry : params.entrySet()) {
            Specification<Product> productSpecification = Specification.where(specificationManager
                    .get(entry.getKey(), entry.getValue().split(",")));
            specification = specification == null ? Specification.where(productSpecification)
                    : specification.and(productSpecification);
        }
        return repository.findAll(specification, pageable).getContent();
    }
}
