package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.mapper.ProductDtoRequestMapper;
import mate.academy.springboot.swagger.mapper.ProductDtoResponseMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.model.dto.ProductRequestDto;
import mate.academy.springboot.swagger.model.dto.ProductResponseDto;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.util.ParsingSortOrder;
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
    private final ProductDtoRequestMapper productDtoRequestMapper;
    private final ProductDtoResponseMapper productDtoResponseMapper;

    public ProductController(ProductService productService,
                             ProductDtoRequestMapper productDtoRequestMapper,
                             ProductDtoResponseMapper productDtoResponseMapper) {
        this.productService = productService;
        this.productDtoRequestMapper = productDtoRequestMapper;
        this.productDtoResponseMapper = productDtoResponseMapper;
    }

    @ApiOperation(value = "Create a new product")
    @PostMapping
    public ProductResponseDto add(@RequestBody ProductRequestDto dto) {
        Product product = productService.save(productDtoRequestMapper.fromDto(dto));
        return productDtoResponseMapper.toDto(product);
    }

    @ApiOperation(value = "Get product by id")
    @GetMapping("/{id}")
    public ProductResponseDto get(@PathVariable Long id) {
        return productDtoResponseMapper.toDto(productService.get(id));
    }

    @ApiOperation(value = "Get all products with pagination and ability"
            + " to sort by price or by title in ASC or DESC order")
    @GetMapping
    public List<ProductResponseDto> findAll(
            @RequestParam @ApiParam(defaultValue = "20") Integer count,
            @RequestParam @ApiParam(defaultValue = "0") Integer page,
            @RequestParam @ApiParam(defaultValue = "id") String sortBy) {
        Sort sort = Sort.by(ParsingSortOrder.getSortOrders(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest).stream()
                .map(productDtoResponseMapper::toDto)
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "Update product by id")
    @PutMapping("/{id}")
    public ProductResponseDto update(@PathVariable Long id, @RequestBody ProductRequestDto dto) {
        Product product = productDtoRequestMapper.fromDto(dto);
        product.setId(id);
        return productDtoResponseMapper.toDto(productService.update(product));
    }

    @ApiOperation(value = "Delete product by id")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @ApiOperation(value = "Get all products where price is between"
            + " two values received as a `RequestParam` inputs")
    @GetMapping("/price-between")
    public List<ProductResponseDto> findAllByPriceBetween(
            @RequestParam BigDecimal priceFrom,
            @RequestParam BigDecimal priceTo,
            @RequestParam @ApiParam(defaultValue = "20") Integer count,
            @RequestParam @ApiParam(defaultValue = "0") Integer page,
            @RequestParam @ApiParam(defaultValue = "id") String sortBy) {
        Sort sort = Sort.by(ParsingSortOrder.getSortOrders(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAllByPriceBetween(priceFrom, priceTo, pageRequest).stream()
            .map(productDtoResponseMapper::toDto)
            .collect(Collectors.toList());
    }
}
