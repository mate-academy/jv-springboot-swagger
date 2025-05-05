package mate.academy.springboot.swagger.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.repository.ProductRepository;
import mate.academy.springboot.swagger.repository.specification.ProductSpecificationManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductServicesImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductSpecificationManager productSpecificationManager;

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product get(Long id) {
        return productRepository.getById(id);
    }

    @Override
    public List<Product> findAll(Map<String, String> params,
                                 Set<String> ignoreRequestParams,
                                 PageRequest pageRequest) {
        Specification<Product> totalSpecification = null;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (ignoreRequestParams.contains(entry.getKey())) {
                continue;
            }
            Specification<Product> localSpecification = productSpecificationManager.get(
                    entry.getKey(),
                    entry.getValue().split(","));
            totalSpecification = totalSpecification == null
                    ? Specification.where(localSpecification)
                    : totalSpecification.and(localSpecification);
        }
        return productRepository.findAll(totalSpecification, pageRequest).toList();
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
