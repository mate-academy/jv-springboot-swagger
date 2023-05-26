package mate.academy.springboot.swagger.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.dto.mapper.DtoMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.util.PageableUtil;
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

@AllArgsConstructor
@RestController
@RequestMapping(name = "/products")
public class ProductController {
    private final ProductService productService;
    private final PageableUtil pageableUtil;
    private final DtoMapper<Product, ProductRequestDto, ProductResponseDto> dtoMapper;

    @Operation(summary = "Get Product by Id")
    @GetMapping("/{id}")
    public ProductResponseDto getById(@Parameter(description = "Product search identifier")
                                          @PathVariable Long id) {
        return dtoMapper.mapToDto(productService.getById(id));
    }

    @Operation(summary = "Add Product to Data Base")
    @PostMapping
    public ProductResponseDto create(@Parameter(description =
            "Product update DTO (product title "
                    + "and product price)") @RequestBody ProductRequestDto dto) {
        return dtoMapper.mapToDto(productService.create(dtoMapper.mapToModel(dto)));
    }

    @Operation(summary = "Update Product in Data Base")
    @PutMapping("/{id}")
    public ProductResponseDto update(@PathVariable Long id, @Parameter(description =
            "Product update DTO (product title "
                    + "and product price)") @RequestBody ProductRequestDto dto) {
        Product product = dtoMapper.mapToModel(dto);
        product.setId(id);
        return dtoMapper.mapToDto(productService.update(product));
    }

    @Operation(summary = "Delete Product by ID from Data Base")
    @DeleteMapping("/{id}")
    public void delete(@Parameter(description = "Product delete identifier")
                           @PathVariable Long id) {
        productService.deleteById(id);
    }

    @Operation(summary = "Get all Products by price between two values",
            description = "Get all Products by price between two values. "
            + "It is possible to use pagination")
    @GetMapping("/by-price")
    public List<ProductResponseDto> findAllByPriceBetween(@Parameter(
            description = "A value that indicates the price From "
                    + "which the limit should be filtered")
                                                              @RequestParam BigDecimal from,
                                                          @Parameter(
                                                                  description = "A value "
                                                                          + "that indicates "
                                                                          + "the price To "
                                                                          + "which the "
                                                                          + "limit should "
                                                                          + "be filtered")
                                                          @RequestParam BigDecimal to,
                                                          @Parameter(
                                                                  description = "Number "
                                                                          + "of products on page. "
                                                                  + "Default value = 10")
                                                          @RequestParam (
                                                                  defaultValue = "10")
                                                              Integer count,
                                                          @Parameter(
                                                                  description = "Number of pages. "
                                                                  + "Default value = 0")
                                                          @RequestParam (
                                                                  defaultValue = "0")
                                                              Integer page,
                                                          @Parameter(
                                                                  description = "Sort parameter. "
                                                                  + "Default value - id")
                                                          @RequestParam (defaultValue = "id")
                                                              String sortBy) {
        final PageRequest pageRequest = pageableUtil.createPageRequest(count, page, sortBy);
        return productService.findAllByPriceBetween(from,
                        to,
                        pageableUtil.createPageRequest(count, page, sortBy))
                .stream()
                .map(dtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Get all Products",
            description = "Get all Products. "
            + "It is possible to use pagination")
    @GetMapping
    public List<ProductResponseDto> findAll(@Parameter(description = "Number of products on page. "
            + "Default value = 10")
                                                @RequestParam (defaultValue = "10") Integer count,
                                            @Parameter(description = "Number of pages. "
                                                    + "Default value = 0")
                                            @RequestParam (defaultValue = "0") Integer page,
                                            @Parameter(description = "Sort parameter. "
                                                    + "Default value - id")
                                            @RequestParam (defaultValue = "id") String sortBy) {
        return productService.findAll(pageableUtil.createPageRequest(count, page, sortBy))
                .stream()
                .map(dtoMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
