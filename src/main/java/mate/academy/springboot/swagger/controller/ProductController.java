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
import mate.academy.springboot.swagger.service.mapper.ProductMapper;
import mate.academy.springboot.swagger.util.SortingOrders;
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
    private final SortingOrders sortingOrders;
    private final ProductMapper productMapper;

    public ProductController(ProductService productService, SortingOrders sortingOrders,
            ProductMapper productMapper) {
        this.productService = productService;
        this.sortingOrders = sortingOrders;
        this.productMapper = productMapper;
    }

    @GetMapping("/{id}")
    @ApiOperation("Get product by id")
    public ProductResponseDto getById(@PathVariable Long id) {
        return productMapper.mapToDto(productService.get(id));
    }

    @PostMapping
    @ApiOperation("Create product")
    public ProductResponseDto create(@RequestBody ProductRequestDto requestDto) {
        return productMapper.mapToDto(productService
                .save(productMapper.mapToModel(requestDto)));
    }

    @PutMapping("/{id}")
    @ApiOperation("Update product")
    public ProductResponseDto update(@PathVariable Long id,
            @RequestBody ProductRequestDto requestDto) {
        Product product = productMapper.mapToModel(requestDto);
        product.setId(id);
        return productMapper.mapToDto(productService.update(product));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete product")
    public void deleteById(@PathVariable Long id) {
        productService.delete(id);
    }

    @GetMapping
    public List<ProductResponseDto> getAll(
            @RequestParam(defaultValue = "10")
            @ApiParam(value = "default value = 10")
            Integer count,
            @RequestParam(defaultValue = "0")
            @ApiParam(value = "default value = 0")
            Integer page,
            @RequestParam(defaultValue = "title")
            @ApiParam(defaultValue = "title")
            String sortBy
    ) {
        Sort sort = sortingOrders.sortBy(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest).stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/price")
    @ApiOperation("Get all by price with pagination")
    public List<ProductResponseDto> getAllByPrice(
            @RequestParam(defaultValue = "20")
            @ApiParam(value = "default value is 20")
            Integer count,
            @RequestParam (defaultValue = "0")
            @ApiParam(value = "default value is 0")
            Integer page,
            @RequestParam (defaultValue = "title")
            @ApiParam(value = "default value is title")
            String sortBy,
            @RequestParam BigDecimal from,
            @RequestParam BigDecimal to) {
        Sort sort = sortingOrders.sortBy(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAllByPriceBetween(pageRequest, from, to).stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }

}
