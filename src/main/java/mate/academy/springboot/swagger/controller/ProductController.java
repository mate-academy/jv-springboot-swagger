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
import org.springframework.data.domain.PageRequest;
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
    @ApiOperation(value = "create a new product")
    public ProductResponseDto save(@RequestBody ProductRequestDto requestDto) {
        Product product = productMapper.toModel(requestDto);
        productService.save(product);
        return productMapper.toResponseDto(product);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by `id`")
    public ProductResponseDto getById(@PathVariable Long id) {
        return productMapper.toResponseDto(productService.get(id));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update product by `id`")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto requestDto) {
        Product product = productMapper.toModel(requestDto);
        product.setId(id);
        productService.update(product);
        return productMapper.toResponseDto(product);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete product by `id`")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @GetMapping("/sort")
    @ApiOperation(value = "Gets all products with "
            + "pagination and ability to sort by price or"
            + " by title in ASC or DESC order")
    public List<ProductResponseDto> findAll(
            @RequestParam(defaultValue = "20")
            @ApiParam(value = "default value is `20`")
            Integer count,
            @RequestParam(defaultValue = "0")
            @ApiParam(value = "default value is `0`")
            Integer page,
            @RequestParam(defaultValue = "id")
            @ApiParam(value = "default value is `id` ")
            String sortBy) {
        PageRequest pageRequest = productService.getPageRequest(page, count, sortBy);
        return productService.findAll(pageRequest)
                .stream()
                .map(productMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/price")
    @ApiOperation(value = "Gets all products list between prices `FROM` and `TO`, "
            + "ability to sort by price or by title in ASC or DESC order")
    public List<ProductResponseDto> getProductBetweenPrices(
            @RequestParam BigDecimal from,
            @RequestParam BigDecimal to,
            @RequestParam(defaultValue = "20")
            @ApiParam(value = "default value is `20`")
            Integer count,
            @RequestParam(defaultValue = "0")
            @ApiParam(value = "default value is `0`")
            Integer page,
            @RequestParam(defaultValue = "id")
            @ApiParam(value = "default value is `id` ")
            String sortBy) {
        PageRequest pageRequest = productService.getPageRequest(page, count, sortBy);
        return productService.findAllBetweenPrices(from, to, pageRequest)
                .stream()
                .map(productMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
