package mate.academy.springboot.swagger.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.dto.mapper.DtoMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.util.SortUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private final SortUtil sortUtil;
    private final DtoMapper<ProductRequestDto, ProductResponseDto, Product> productMapper;

    public ProductController(ProductService productService, SortUtil sortUtil,
                             DtoMapper<ProductRequestDto,
                                     ProductResponseDto,
                                     Product> productMapper) {
        this.productService = productService;
        this.sortUtil = sortUtil;
        this.productMapper = productMapper;
    }

    @Operation(summary = "Add product", description = "Add product to DB")
    @PostMapping
    public ProductResponseDto add(@RequestBody ProductRequestDto productRequestDto) {
        Product product = productService.save(productMapper.mapToModel(productRequestDto));
        return productMapper.mapToDto(product);
    }

    @Operation(summary = "Get product by id", description = "Get product by id")
    @GetMapping("/{id}")
    public ProductResponseDto get(@PathVariable Long id) {
        return productMapper.mapToDto(productService.getById(id));
    }

    @Operation(summary = "Delete product by id", description = "Delete product by id")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @Operation(summary = "Update product by id", description = "Update product by id")
    @PutMapping("/{id}")
    public void update(@PathVariable Long id,
                @RequestBody ProductRequestDto productRequestDto) {
        Product product = productMapper.mapToModel(productRequestDto);
        product.setId(id);
        productService.save(product);
    }

    @Operation(summary = "Get all products", description = "Get all products. "
            + "You can set count products per page, page and sorting rules")
    @GetMapping
    public List<ProductResponseDto> findAll(@Parameter(description = "Number of products on page",
            example = "10", schema = @Schema(type = "integer", defaultValue = "10"))
                                     @RequestParam(defaultValue = "10") Integer count,
                                     @Parameter(description = "Number of page", example = "0",
                                     schema = @Schema(type = "integer", defaultValue = "0"))
                                     @RequestParam(defaultValue = "0") Integer page,
                                     @Parameter(description = "By what parameter will "
                                     + "the sorting be", example = "id",
                                     schema = @Schema(type = "String", defaultValue = "id"))
                                     @RequestParam(defaultValue = "id") String sortBy) {
        Sort sort = Sort.by(sortUtil.getSort(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest).stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Get all products between two price levels ",
            description = "Get all products "
            + "between two price levels. "
            + "You can set count products per page, page and sorting rules")
    @GetMapping("/by-price")
    public List<ProductResponseDto> findAllByPrice(@RequestParam BigDecimal from,
                                                   @RequestParam BigDecimal to,
                                    @Parameter(description = "Number of products on page",
                                            example = "10",
                                    schema = @Schema(type = "integer", defaultValue = "10"))
                                    @RequestParam(defaultValue = "10") Integer count,
                                    @Parameter(description = "Number of page", example = "0",
                                    schema = @Schema(type = "integer", defaultValue = "0"))
                                    @RequestParam(defaultValue = "0") Integer page,
                                    @Parameter(description = "By what parameter will "
                                            + "the sorting be", example = "id",
                                    schema = @Schema(type = "String", defaultValue = "id"))
                                    @RequestParam(defaultValue = "id") String sortBy) {
        Sort sort = Sort.by(sortUtil.getSort(sortBy));
        Pageable pg = PageRequest.of(page, count, sort);
        return productService.findAllByPriceBetween(from, to, pg).stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
