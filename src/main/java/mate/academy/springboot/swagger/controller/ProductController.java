package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import mate.academy.springboot.swagger.dto.ProductMapper;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.PageableSupplier;
import mate.academy.springboot.swagger.service.ProductService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductMapper mapper;
    private final ProductService service;
    private final PageableSupplier pageableSupplier;

    public ProductController(ProductMapper mapper, ProductService service,
                             PageableSupplier pageableSupplier) {
        this.mapper = mapper;
        this.service = service;
        this.pageableSupplier = pageableSupplier;
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) {
        return service.get(id);
    }

    @PostMapping
    public Product create(@RequestBody ProductRequestDto productRequestDto) {
        Product product = mapper.toModel(productRequestDto);
        return service.save(product);
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @RequestBody ProductRequestDto productRequestDto) {
        Product product = mapper.toModel(productRequestDto);
        product.setId(id);
        return service.update(product);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.remove(id);
    }

    @GetMapping
    public List<Product> findAll(@RequestParam(defaultValue = "10") Integer count,
                                 @RequestParam(defaultValue = "0") Integer page,
                                 @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = pageableSupplier.get(count, page, sortBy);
        return service.findAll(pageable);
    }

    @GetMapping("/by-price")
    public List<Product> findAllByPrice(@RequestParam BigDecimal from,
                                        @RequestParam BigDecimal to,
                                        @RequestParam(defaultValue = "10") Integer count,
                                        @RequestParam(defaultValue = "0") Integer page,
                                        @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = pageableSupplier.get(count, page, sortBy);
        return service.findAll(from, to, pageable);
    }


}
