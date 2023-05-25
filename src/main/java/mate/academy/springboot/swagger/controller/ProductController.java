package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.mapper.DtoMapper;
import mate.academy.springboot.swagger.util.AppUtil;
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

@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
    private final DtoMapper<Product, ProductRequestDto, ProductResponseDto> mapper;
    private final ProductService productService;

    @PostMapping
    @ApiOperation(value = "create new product")
    public ProductResponseDto create(@RequestBody ProductRequestDto productRequestDto) {
        return mapper.toDto(productService.create(mapper.toModel(productRequestDto)));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "update product by id")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto productRequestDto) {
        Product product = mapper.toModel(productRequestDto);
        product.setId(id);
        return mapper.toDto(productService.update(product));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "delete product by id")
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "get product by id")
    public ProductResponseDto getById(@PathVariable Long id) {
        return mapper.toDto(productService.getById(id));
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "get list of products where price between (from and to) values")
    public List<ProductResponseDto> getAllProductsByPriceBetween(
            @RequestParam BigDecimal from,
            @RequestParam BigDecimal to,
            @RequestParam(defaultValue = "20")
            @ApiParam(value = "default value is `20`") Integer count,
            @RequestParam(defaultValue = "0")
            @ApiParam(value = "default value is `0`") Integer page,
            @RequestParam(defaultValue = "id")
            @ApiParam(value = "default value is `id`") String sortBy) {
        List<Sort.Order> orders = AppUtil.sortOrders(sortBy);
        Sort sort = Sort.by(orders);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.getAllProductsByPriceBetween(from, to, pageRequest)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping
    @ApiOperation(value = "get list of products")
    public List<ProductResponseDto> getAllProducts(
            @RequestParam(defaultValue = "20")
            @ApiParam(value = "default value is `20`") Integer count,
            @RequestParam(defaultValue = "0")
            @ApiParam(value = "default value is `0`") Integer page,
            @RequestParam(defaultValue = "id")
            @ApiParam(value = "default value is `id`") String sortBy) {
        Sort sort = Sort.by(AppUtil.sortOrders(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.getAllProducts(pageRequest)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
