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
import mate.academy.springboot.swagger.service.mapper.DtoMapper;
import mate.academy.springboot.swagger.service.mapper.ProductMapper;
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
    private final DtoMapper<Product, ProductRequestDto, ProductResponseDto> productMapper;

    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @PostMapping
    @ApiOperation(value = "Create new Product")
    public ProductResponseDto crate(@RequestBody ProductRequestDto productRequestDto) {
        Product product = productService.create(productMapper.mapToModel(productRequestDto));
        return productMapper.mapToDto(product);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get Product by ID")
    public ProductResponseDto getById(@PathVariable Long id) {
        return productMapper.mapToDto(productService.getById(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete Product by ID")
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update Product")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto productRequestDto) {
        Product product = productMapper.mapToModel(productRequestDto);
        product.setId(id);
        productService.update(product);
        return productMapper.mapToDto(product);
    }

    @GetMapping
    @ApiOperation(value = "Get Products by parameters")
    public List<ProductResponseDto> getAllProductByParam(
            @RequestParam(defaultValue = "10") @ApiParam("Default value is 10") Integer count,
            @RequestParam(defaultValue = "0") @ApiParam("Default value is 0") Integer page,
            @RequestParam(defaultValue = "id") @ApiParam("Default value is id") String sortBy) {
        return productService.findAllProductWithParam(count, page, sortBy)
                .stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-prise")
    @ApiOperation(value = "Get Products between prises")
    public List<ProductResponseDto> getProductByPriseBetween(
            @RequestParam BigDecimal from,
            @RequestParam BigDecimal to,
            @RequestParam(defaultValue = "10") @ApiParam("Default value is 10") Integer count,
            @RequestParam(defaultValue = "0") @ApiParam("Default value is 0") Integer page,
            @RequestParam(defaultValue = "id") @ApiParam("Default value is id") String sortBy) {
        return productService.findAllByPriseBetween(from, to, count, page, sortBy)
                .stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
