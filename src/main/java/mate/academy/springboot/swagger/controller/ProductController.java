package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.SortService;
import mate.academy.springboot.swagger.service.mapper.ProductDtoMapper;
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
    private final SortService sortService;
    private final ProductDtoMapper productDtoMapper;

    public ProductController(ProductService productService,
                             SortService sortService, ProductDtoMapper productDtoMapper) {
        this.productService = productService;
        this.sortService = sortService;
        this.productDtoMapper = productDtoMapper;
    }

    @PostMapping
    @ApiOperation(value = "create new product")
    public ProductResponseDto create(@RequestBody ProductRequestDto productRequestDto) {
        Product product = productService.create(productDtoMapper.toModel(productRequestDto));
        return productDtoMapper.mapToDto(product);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by id")
    public ProductResponseDto getById(@PathVariable Long id) {
        return productDtoMapper.mapToDto(productService.getById(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "deleting product by id")
    void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "updating product by id")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto productRequestDto) {
        Product product = productService.getById(id);
        product.setTitle(productRequestDto.getTitle());
        product.setPrice(productRequestDto.getPrice());
        return productDtoMapper.mapToDto(productService.update(product));
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "get all products where price is between two values"
            + " Add pagination and ability to sort by price or by title in ASC or DESC order")
    public List<ProductResponseDto> getAllByPriceBetween(
            @RequestParam BigDecimal from, @RequestParam BigDecimal to,
            @RequestParam (defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer count,
            @RequestParam(defaultValue = "id") String sortBy) {
        Sort sort = sortService.sort(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.getAllByPriceBetween(from, to, pageRequest)
                .stream().map(productDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping
    @ApiOperation(value = "get all products with pagination and ability"
            + " to sort by price or by title in ASC or DESC order")
    public List<ProductResponseDto> findAll(@RequestParam(defaultValue = "0") Integer page,
                                            @RequestParam(defaultValue = "5") Integer count,
                                            @RequestParam(defaultValue = "id") String sortBy) {
        Sort sort = sortService.sort(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.getAll(pageRequest)
                .stream()
                .map(productDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
