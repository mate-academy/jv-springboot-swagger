package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.mapper.ProductDtoMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.util.SortUtil;
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
@RequestMapping(value = "/products")
public class ProductController {
    private final ProductService productService;
    private final ProductDtoMapper productDtoMapper;
    private final SortUtil sortUtil;

    public ProductController(ProductService productService,
                             ProductDtoMapper productDtoMapper,
                             SortUtil sortUtil) {
        this.productService = productService;
        this.productDtoMapper = productDtoMapper;
        this.sortUtil = sortUtil;
    }

    @PostMapping
    @ApiOperation(value = "create new product")
    public ProductResponseDto create(@RequestBody ProductRequestDto requestDto) {
        Product product = productService.save(
                productDtoMapper.mapToModel(requestDto));
        return productDtoMapper.mapToDto(product);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "get product by id")
    public ProductResponseDto getById(@PathVariable Long id) {
        Product product = productService.getById(id);
        return productDtoMapper.mapToDto(product);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "delete product by id")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "update product by id")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestParam ProductRequestDto requestDto) {
        Product product = productDtoMapper.mapToModel(requestDto);
        product.setId(id);
        productService.update(product);
        return productDtoMapper.mapToDto(product);
    }

    @GetMapping
    @ApiOperation(value = "get products list")
    public List<ProductResponseDto> getAllProducts(
            @RequestParam(defaultValue = "10")
            @ApiParam(value = "default value is 10") Integer count,
            @RequestParam(defaultValue = "0")
            @ApiParam(value = "default value is 0") Integer page,
            @RequestParam(defaultValue = "id") String sortBy) {
        return productService.findAll(sortUtil.getSortedPage(count, page, sortBy))
                .stream()
                .map(productDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "get products by price between")
    public List<ProductResponseDto> getAllByPrice(
            @RequestParam BigDecimal from,
            @RequestParam BigDecimal to,
            @RequestParam(defaultValue = "10")
            @ApiParam(value = "default value is 10") Integer count,
            @RequestParam(defaultValue = "0")
            @ApiParam(value = "default value is 0") Integer page,
            @RequestParam(defaultValue = "id") String sortBy) {
        return productService.getAllByPriceBetween(from, to,
                        sortUtil.getSortedPage(count, page, sortBy))
                .stream()
                .map(productDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
