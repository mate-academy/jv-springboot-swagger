package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.SortingService;
import mate.academy.springboot.swagger.service.mapper.ProductMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    private final ProductService productService;
    private final ProductMapper productMapper;

    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @PostMapping
    @ApiOperation(value = "Create new product")
    public ProductResponseDto create(@RequestBody ProductRequestDto productRequestDto) {
        Product product = productService.save(productMapper.toModel(productRequestDto));
        return productMapper.toDto(product);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by id")
    public ProductResponseDto get(@PathVariable Long id) {
        return productMapper.toDto(productService.get(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete product by id")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update product by id")
    public ProductResponseDto update(@PathVariable Long id,
            @RequestBody ProductRequestDto productRequestDto) {
        Product product = productMapper.toModel(productRequestDto);
        product.setId(id);
        return productMapper.toDto(productService.save(product));
    }

    @GetMapping
    @ApiOperation(value = "Get all products. Sort by title or/and by price")
    public List<ProductResponseDto> getAll(@RequestParam (defaultValue = "20")
                                               @ApiParam(value = "Default value is 20")
                                               Integer count,
                                           @RequestParam (defaultValue = "0")
                                           @ApiParam(value = "DefaultValue is 0")
                                           Integer page,
                                           @RequestParam (defaultValue = "id")
                                               @ApiParam(value = "Default value is id")
                                               String sortBy) {
        Sort sort = Sort.by(SortingService.parseSortingOrders(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.getAll(pageRequest).stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "Get all products with price between two values. "
            + "Sort by title or/and by price")
    public List<ProductResponseDto> getAllBetween(@RequestParam BigDecimal fromPrice,
                                                  @RequestParam BigDecimal toPrice,
                                                  @RequestParam (defaultValue = "20")
                                                      @ApiParam(value = "Default value is 20")
                                                      Integer count,
                                                  @RequestParam (defaultValue = "0")
                                                      @ApiParam(value = "DefaultValue is 0")
                                                      Integer page,
                                                  @RequestParam (defaultValue = "id")
                                                      @ApiParam(value = "Default value is id")
                                                      String sortBy) {
        Sort sort = Sort.by(SortingService.parseSortingOrders(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.getAllBetween(fromPrice, toPrice, pageRequest).stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }
}
