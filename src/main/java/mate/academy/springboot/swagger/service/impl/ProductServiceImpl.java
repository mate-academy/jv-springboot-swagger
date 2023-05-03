package mate.academy.springboot.swagger.service.impl;

import java.util.NoSuchElementException;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.repository.ProductRepository;
import mate.academy.springboot.swagger.repository.specification.SpecificationManager;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.handler.PaginationAndSortingHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

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
    public Page<Product> getAll(String page,
                                String count,
                                String sortBy,
                                String priceFrom,
                                String priceTo) {
        Specification<Product> priceFromSpecification = specificationManager
                .get("priceFrom", priceFrom);
        Specification<Product> priceToSpecification = specificationManager
                .get("priceTo", priceTo);
        return repository.findAll(Specification.where(priceFromSpecification)
                        .and(priceToSpecification),
                paginationAndSortingHandler.handle(page, count, sortBy));
    }
}
