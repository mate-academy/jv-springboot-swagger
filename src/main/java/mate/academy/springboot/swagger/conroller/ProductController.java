package mate.academy.springboot.swagger.conroller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.mapper.DtoMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.PaginationService;
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

@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final DtoMapper<Product, ProductRequestDto, ProductResponseDto> dtoMapper;
    private final PaginationService paginationService;

    @Operation(summary = "Add product to DB")
    @PostMapping
    public ProductResponseDto create(@Parameter(description = "Product to add", required = true,
            schema = @Schema(implementation = ProductRequestDto.class))
                                         @RequestBody ProductRequestDto dto) {
        return dtoMapper.mapToDto(productService.create(dtoMapper.mapToModel(dto)));
    }

    @Operation(summary = "Get product by id")
    @GetMapping("/{id}")
    public ProductResponseDto findById(@PathVariable Long id) {
        return dtoMapper.mapToDto(productService.findById(id));
    }

    @Operation(summary = "Delete product by id from DB")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @Operation(summary = "Update product in DB")
    @PutMapping("/{id}")
    public ProductResponseDto update(@PathVariable Long id,
                                     @Parameter(description = "Product to update",
            required = true,
            schema = @Schema(implementation = ProductRequestDto.class))
                                     @RequestBody ProductRequestDto dto) {
        Product product = productService.update(id, dtoMapper.mapToModel(dto));
        return dtoMapper.mapToDto(product);
    }

    @Operation(summary = "Get all Products by price between two values",
            description = "It is possible to use pagination")
    @GetMapping("/by-price")
    public List<ProductResponseDto> findAllByPriceBetween(@Parameter(
            description = "A value that indicates the price from "
                    + "which the limit should be filtered") @RequestParam BigDecimal from,
                                                          @Parameter(
                                                                  description = "A value "
                                                                          + "that indicates "
                                                                          + "the price to "
                                                                          + "which the "
                                                                          + "limit should "
                                                                          + "be filtered")
                                                          @RequestParam BigDecimal to,
                                                          @Parameter(
                                                                  description = "Number "
                                                                          + "of products per page.")
                                                          @RequestParam (
                                                                  defaultValue = "10")
                                                          Integer count,
                                                          @Parameter(
                                                                  description = "Number of pages.")
                                                          @RequestParam (
                                                                  defaultValue = "0")
                                                          Integer page,
                                                          @Parameter(
                                                                  description = "Sort parameter.")
                                                          @RequestParam (
                                                                  defaultValue = "id")
                                                          String sortBy) {
        final PageRequest pageRequest = paginationService.createPageRequest(count, page, sortBy);
        return productService.findAllByPriceBetween(from,
                        to,
                        paginationService.createPageRequest(count, page, sortBy))
                .stream()
                .map(dtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Get all Products",
            description = "It is possible to use pagination")
    @GetMapping
    public List<ProductResponseDto> findAll(@Parameter(description = "Number of products on page.")
                                                @RequestParam (defaultValue = "10") Integer count,
                                            @Parameter(description = "Number of pages.")
                                            @RequestParam (defaultValue = "0") Integer page,
                                            @Parameter(description = "Sort parameter.")
                                            @RequestParam (defaultValue = "id") String sortBy) {
        return productService.findAll(paginationService.createPageRequest(count, page, sortBy))
                .stream()
                .map(dtoMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
