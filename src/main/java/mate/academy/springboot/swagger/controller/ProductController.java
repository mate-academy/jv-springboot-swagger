package mate.academy.springboot.swagger.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.dto.mapper.ProductMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.util.SortOrderParserUtil;
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
    @Operation(summary = "Create product", description = "Add a new product to storage")
    public ProductResponseDto create(@RequestBody ProductRequestDto requestDto) {
        Product product = productService.saveOrUpdate(productMapper.mapToModel(requestDto));
        return productMapper.mapToDto(product);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product by id")
    public void deleteById(@Parameter(description = "Product id to delete")
                           @PathVariable Long id) {
        productService.deleteById(id);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find product by id")
    public ProductResponseDto findById(@Parameter(description = "Product id to find")
                                       @PathVariable Long id) {
        return productMapper.mapToDto(productService.findById(id));
    }

    @GetMapping
    @Operation(summary = "Find all products by page and sort")
    public List<ProductResponseDto> findAll(
            @Parameter(description = "Page number with default value `0`")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Product count per page with default value `10`")
            @RequestParam(defaultValue = "10") Integer size,
            @Parameter(description = "Sort by price or title in ASC or DESC (case insensitive) "
                    + "order. Default sort by `id`. Default order is ascending. "
                    + "Examples, `price:desc`, `price:asc` or just`price`")
            @RequestParam(defaultValue = "id") String sort) {
        PageRequest pageRequest = PageRequest.of(page, size, SortOrderParserUtil.getSort(sort));
        return productService.findAll(pageRequest).stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @Operation(summary = "Find products by price, by page and sort")
    public List<ProductResponseDto> findAllByPrice(
            @Parameter(description = "Minimum price") @RequestParam BigDecimal from,
            @Parameter(description = "Maximum price") @RequestParam BigDecimal to,
            @Parameter(description = "Page number with default value `0`")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Product count per page with default value `10`")
            @RequestParam(defaultValue = "10") Integer size,
            @Parameter(description = "Sort by price or title in ASC or DESC (case insensitive) "
                    + "order. Default sort by `id`. Default order is ascending. "
                    + "Examples, `price:desc`, `price:asc`  or just`price`")
            @RequestParam(defaultValue = "id") String sort) {
        PageRequest pageRequest =
                PageRequest.of(page, size, SortOrderParserUtil.getSort(sort));
        return productService.findAllByPriceBetween(from, to, pageRequest).stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update product by id")
    public ProductResponseDto updateById(@Parameter(description = "Product id to update")
                                         @PathVariable Long id,
                                         @RequestBody ProductRequestDto requestDto) {
        Product product = productMapper.mapToModel(requestDto);
        product.setId(id);
        return productMapper.mapToDto(productService.saveOrUpdate(product));
    }
}
