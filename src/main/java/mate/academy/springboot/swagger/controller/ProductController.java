package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.dto.ProductMapper;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.PageableProvider;
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

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductMapper mapper;
    private final ProductService service;
    private final PageableProvider pageableProvider;

    public ProductController(ProductMapper mapper, ProductService service,
                             PageableProvider pageableProvider) {
        this.mapper = mapper;
        this.service = service;
        this.pageableProvider = pageableProvider;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Gets product by id")
    public Product getById(@PathVariable @ApiParam(name = "id",
            value = "id of requesting product", required = true) Long id) {
        return service.get(id);
    }

    @PostMapping
    @ApiOperation(value = "Creates new Product")
    public Product create(@RequestBody ProductRequestDto productRequestDto) {
        Product product = mapper.toModel(productRequestDto);
        return service.save(product);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Updates existing Product by id")
    public Product update(@PathVariable @ApiParam(name = "id",
            value = "id of updating product", required = true) Long id,
                          @RequestBody ProductRequestDto productRequestDto) {
        Product product = mapper.toModel(productRequestDto);
        product.setId(id);
        return service.update(product);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deletes existing Product by id")
    public void delete(@PathVariable @ApiParam(name = "id",
            value = "id of deleting product", required = true) Long id) {
        service.remove(id);
    }

    @GetMapping
    @ApiOperation(value = "Gets all products")
    public List<Product> findAll(@RequestParam(defaultValue = "10") @ApiParam(name = "count",
            value = "count of items on page") Integer count,
                                 @RequestParam(defaultValue = "0") @ApiParam(name = "page",
                                         value = "number of page") Integer page,
                                 @RequestParam(defaultValue = "id") @ApiParam(name = "sortBy",
                                         value = "sortBy string") String sortBy) {
        Pageable pageable = pageableProvider.get(count, page, sortBy);
        return service.findAll(pageable);
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "Gets all products filtered by price")
    public List<Product> findAllByPrice(@RequestParam
                                            @ApiParam(name = "count",
                                                    value = "count of items on page",
                                                    required = true)
                                                    BigDecimal from,
                                        @RequestParam
                                        @ApiParam(name = "count",
                                                value = "count of items on page",
                                                required = true)
                                                BigDecimal to,
                                        @RequestParam(defaultValue = "10")
                                            @ApiParam(name = "count",
                                                    value = "count of items on page")
                                                    Integer count,
                                        @RequestParam(defaultValue = "0")
                                            @ApiParam(name = "page", value = "number of page")
                                                    Integer page,
                                        @RequestParam(defaultValue = "id")
                                            @ApiParam(name = "sortBy", value = "sortBy string")
                                                    String sortBy) {
        Pageable pageable = pageableProvider.get(count, page, sortBy);
        return service.findAll(from, to, pageable);
    }
}
