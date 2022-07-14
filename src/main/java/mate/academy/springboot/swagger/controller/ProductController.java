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
    private final SortingService sortingService;
    private final ProductMapper productMapper;

    public ProductController(ProductService productService,
                             SortingService sortingService,
                             ProductMapper productMapper) {
        this.productService = productService;
        this.sortingService = sortingService;
        this.productMapper = productMapper;
    }

    @PostMapping
    @ApiOperation(value = "Add new product")
    public ProductResponseDto create(@RequestBody ProductRequestDto requestDto) {
        return productMapper.toDto(productService.add(productMapper.toModel(requestDto)));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by id")
    public ProductResponseDto getById(@PathVariable Long id) {
        return productMapper.toDto(productService.getById(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete product by id")
    public void delete(@PathVariable Long id) {
        productService.remove(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update product by id")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto requestDto) {
        Product product = productMapper.toModel(requestDto);
        product.setId(id);
        return productMapper.toDto(productService.add(product));
    }

    @GetMapping
    @ApiOperation(value = "Get all products")
    public List<ProductResponseDto> getAll(@RequestParam(defaultValue = "20")
                                           @ApiParam(value = "Default value is `20`")
                                           Integer count,
                                           @RequestParam(defaultValue = "0")
                                           @ApiParam(value = "Default value is `0`")
                                           Integer page,
                                           @RequestParam(defaultValue = "id")
                                           @ApiParam(value = "Default value is `id`")
                                           String sortBy) {
        Sort sort = Sort.by(sortingService.parseSortedOrders(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.getAllProducts(pageRequest)
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "Get all products where price between")
    public List<ProductResponseDto> getAllByPriceBetween(@RequestParam BigDecimal from,
                                                         @RequestParam BigDecimal to,
                                                         @RequestParam(defaultValue = "20")
                                                         @ApiParam(value = "Default value is `20`")
                                                         Integer count,
                                                         @RequestParam(defaultValue = "0")
                                                         @ApiParam(value = "Default value is `0`")
                                                         Integer page,
                                                         @RequestParam(defaultValue = "id")
                                                         @ApiParam(value = "Default value is `id`")
                                                         String sortBy) {
        Sort sort = Sort.by(sortingService.parseSortedOrders(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.getAllProductsByPriceBetween(from, to, pageRequest)
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }
}
