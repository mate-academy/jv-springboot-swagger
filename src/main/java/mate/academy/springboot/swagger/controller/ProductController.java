package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import mate.academy.springboot.swagger.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.dto.response.ProductResponseDto;
import mate.academy.springboot.swagger.mapper.DtoMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.util.SortUtil;
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
@AllArgsConstructor
@RequestMapping("/products")
@Tag(name = "Product", description = "Product API")
public class ProductController {
    private final DtoMapper<ProductRequestDto, ProductResponseDto, Product> mapper;
    private final ProductService productService;

    @Operation(summary = "Create product")
    @PostMapping
    public ProductResponseDto add(@RequestBody @Valid ProductRequestDto productRequestDto) {
        Product product = productService.save(mapper.mapToModel(productRequestDto));
        return mapper.mapToDto(product);
    }

    @Operation(summary = "Get product by id")
    @GetMapping("/{id}")
    public ProductResponseDto getById(@PathVariable Long id) {
        Product product = productService.get(id);
        return mapper.mapToDto(product);
    }

    @Operation(summary = "Delete product by id")
    @DeleteMapping("/{id}")
    public String removeById(@PathVariable Long id) {
        productService.remove(id);
        return "Product was removed :)";
    }

    @Operation(summary = "Update product")
    @PutMapping("/{id}")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody @Valid ProductRequestDto dto) {
        Product product = mapper.mapToModel(dto);
        product.setId(id);
        return mapper.mapToDto(productService.save(product));
    }

    @Operation(summary = "Get products within price range, pagination and"
            + "ability to sort by price or by title ASC or DESC order")
    @GetMapping("/between-prices")
    public List<ProductResponseDto> getAllBetweenPrice(
            @ApiParam(value = "price from value")
            @RequestParam BigDecimal from,
            @ApiParam(value = "price to value")
            @RequestParam BigDecimal to,
            @ApiParam(value = "page size", defaultValue = "20")
            @RequestParam(defaultValue = "20") Integer count,
            @ApiParam(value = "number of page", defaultValue = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @ApiParam(value = "sort param", defaultValue = "id")
            @RequestParam(defaultValue = "id") String sortBy) {
        Sort sort = Sort.by(SortUtil.parse(sortBy));
        PageRequest request = PageRequest.of(page, count, sort);
        return productService.getAllByPriceBetween(from, to, request).stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/all")
    @Operation(summary = "Get all products with pagination and ability "
            + "to sort by price or by title in ASC or DESC order")
    public List<ProductResponseDto> findAll(@ApiParam(value = "page size", defaultValue = "20")
                                            @RequestParam(defaultValue = "20")
                                            Integer count,
                                            @ApiParam(value = "page number", defaultValue = "0")
                                            @RequestParam(defaultValue = "0")
                                            Integer page,
                                            @ApiParam(value = "sort param", defaultValue = "price")
                                            @RequestParam(defaultValue = "price")
                                            String sortBy) {
        Sort sort = Sort.by(SortUtil.parse(sortBy));
        PageRequest request = PageRequest.of(page, count, sort);
        return productService.getAll(request).stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }
}
