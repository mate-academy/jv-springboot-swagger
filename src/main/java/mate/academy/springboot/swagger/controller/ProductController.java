package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.ProductMapper;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    @ApiOperation(value = "Create a new product.")
    public ProductResponseDto save(@RequestBody ProductRequestDto productRequestDto) {
        Product product = productMapper.toModel(productRequestDto);
        Product savedProduct = productService.save(product);
        return productMapper.toResponseDto(savedProduct);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by id.")
    public ProductResponseDto getById(@PathVariable Long id) {
        Product product = productService.getById(id);
        return productMapper.toResponseDto(product);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete product by id.")
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @PatchMapping("/{id}")
    @ApiOperation(value = "Update existing product with id.")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto requestDto) {
        Product product = productMapper.toModel(requestDto);
        product.setId(id);
        Product updatedProduct = productService.update(product);
        return productMapper.toResponseDto(updatedProduct);
    }

    @GetMapping // pagination + sorting
    @ApiOperation(value = "Get all products with pagination and sorting by price and by title.")
    public List<ProductResponseDto> getAll(@RequestParam (defaultValue = "2") Integer count,
                                           @RequestParam (defaultValue = "0") Integer page,
                                           @RequestParam (defaultValue = "id:ASC") String sortBy) {
        PageRequest pageRequest = productService.getPageRequest(page, count, sortBy);
        return productService.findAll(pageRequest)
                .stream()
                .map(productMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/withPrice") // from-to + pagination + sorting
    @ApiOperation(value = "Get all products with price from...to"
            + " with pagination and sorting by price and by title.")
    public List<ProductResponseDto> getAllByPriceBetween(
            @ApiParam(value = "from price") @RequestParam BigDecimal from,
            @ApiParam(value = "to price") @RequestParam BigDecimal to,

            @ApiParam(value = "products on page, default=20")
            @RequestParam(defaultValue = "20") Integer count,

            @ApiParam(value = "page to show, default=0")
            @RequestParam(defaultValue = "0") Integer page,

            @ApiParam(value = "field to sort by, default=id")
            @RequestParam(defaultValue = "id") String sortBy) {

        PageRequest pageRequest = productService.getPageRequest(page, count, sortBy);
        return productService.findAllWithPrice(from, to, pageRequest)
                .stream()
                .map(productMapper::toResponseDto)
                .collect(Collectors.toList());
    }

}
