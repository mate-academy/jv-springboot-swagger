package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductMapper;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.util.ProductSorter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;
    private final ProductSorter productSorter;

    public ProductController(ProductService productService, ProductMapper productMapper,
                             ProductSorter productSorter) {
        this.productService = productService;
        this.productMapper = productMapper;
        this.productSorter = productSorter;
    }

    @PostMapping
    @ApiOperation(value = "Create a new product")
    public ProductResponseDto create(@RequestBody ProductRequestDto productRequestDto) {
        Product product = productMapper.dtoToModel(productRequestDto);
        return productMapper.modelToDto(productService.save(product));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by id")
    public ProductResponseDto get(@PathVariable Long id) {
        Product product = productService.get(id);
        return productMapper.modelToDto(product);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiOperation(value = "Delete product by id")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update product by id")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto productRequestDto) {
        Product product = productMapper.dtoToModel(productRequestDto);
        product.setId(id);
        return productMapper.modelToDto(productService.update(product));
    }

    @GetMapping
    @ApiOperation(value = "Find all products with pagination and sort parameters")
    public List<ProductResponseDto> findAllPaginationSort(
            @RequestParam (defaultValue = "5")
            @ApiParam(value = "defaultValue is `5`") Integer count,
            @RequestParam (defaultValue = "0")
            @ApiParam(value = "defaultValue is `0`") Integer page,
            @RequestParam (defaultValue = "price")
            @ApiParam(value = "defaultValue is `price`") String sortBy) {
        List<Sort.Order> orders = productSorter.createSortOrders(sortBy);
        Sort sort = productSorter.createSort(orders);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest).stream()
                .map(productMapper::modelToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(
            value = "Find all products between price values with pagination and sort parameters")
    public List<ProductResponseDto> findAllPriceBetween(
            @RequestParam @ApiParam(value = "Price from") BigDecimal from,
            @RequestParam @ApiParam(value = "Price from") BigDecimal to,
            @RequestParam (defaultValue = "2")
            @ApiParam(value = "defaultValue is `5`") Integer count,
            @RequestParam (defaultValue = "0")
            @ApiParam(value = "defaultValue is `0`") Integer page,
            @RequestParam (defaultValue = "price")
            @ApiParam(value = "defaultValue is `price`") String sortBy) {
        List<Sort.Order> orders = productSorter.createSortOrders(sortBy);
        Sort sort = productSorter.createSort(orders);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAllByPriceBetween(from, to, pageRequest).stream()
                .map(productMapper::modelToDto)
                .collect(Collectors.toList());
    }
}
