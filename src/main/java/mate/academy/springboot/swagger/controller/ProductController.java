package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.dto.mapper.ProductMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.SortService;
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
    private final SortService sortService;

    public ProductController(ProductService productService,
                             ProductMapper productMapper,
                             SortService sortService) {
        this.productService = productService;
        this.productMapper = productMapper;
        this.sortService = sortService;
    }

    @PostMapping
    @ApiOperation(value = "Create a new product")
    public ProductResponseDto create(@RequestBody ProductRequestDto productRequestDto) {
        return productMapper.toResponseDto(productService
                .save(productMapper.toModel(productRequestDto)));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get a product by id")
    public ProductResponseDto get(@PathVariable Long id) {
        return productMapper.toResponseDto(productService.get(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a product by id")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update a product by id")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto productRequestDto) {
        Product product = productMapper.toModel(productRequestDto);
        product.setId(id);
        return productMapper.toResponseDto(productService.update(product));
    }

    @GetMapping
    @ApiOperation(value = "Get all products")
    public List<ProductResponseDto> getAll(@ApiParam(value = "default value is '5'")
                                               @RequestParam (defaultValue = "5") Integer count,
                                           @ApiParam(value = "default value is '0'")
                                           @RequestParam (defaultValue = "0") Integer page,
                                           @ApiParam(value = "default value is 'id'")
                                           @RequestParam (defaultValue = "id") String sortBy) {
        Sort sort = sortService.sort(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest).stream()
                .map(productMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "Get all products by price between two values")
    public List<ProductResponseDto> getAllByPriceBetween(@RequestParam BigDecimal from,
                                                         @RequestParam BigDecimal to,
                                                         @ApiParam(value = "default value is '5'")
                                                         @RequestParam (defaultValue = "5")
                                                             Integer count,
                                                         @ApiParam(value = "default value is '0'")
                                                         @RequestParam (defaultValue = "0")
                                                             Integer page,
                                                         @ApiParam(value = "default value is 'id'")
                                                         @RequestParam (defaultValue = "id")
                                                             String sortBy) {
        Sort sort = sortService.sort(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAllByPriceBetween(from, to, pageRequest).stream()
                .map(productMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
