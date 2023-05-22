package controller;

import dto.mapper.DtoMapper;
import dto.request.ProductRequestDto;
import dto.response.ProductResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import model.Product;
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
import service.ProductService;
import util.SortUtil;

@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final DtoMapper<ProductRequestDto, Product, ProductResponseDto> dtoMapper;
    private final SortUtil sortUtil;

    @GetMapping("/{id}")
    @Operation(summary = "Get product by id")
    public ProductResponseDto get(@PathVariable Long id) {
        return dtoMapper.mapToDto(productService.get(id));
    }

    @PostMapping
    @Operation(summary = "Create new product")
    public ProductResponseDto save(@RequestBody ProductRequestDto productRequestDto) {
        return dtoMapper.mapToDto(productService.save(dtoMapper.mapToModel(productRequestDto)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update product by id")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto productRequestDto) {
        Product product = dtoMapper.mapToModel(productRequestDto);
        product.setId(id);
        return dtoMapper.mapToDto(productService.update(product));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete product by id")
    void delete(Long id) {
        productService.delete(id);
    }

    @GetMapping("/all")
    @Operation(summary = "get all products with pagination and ability "
            + "to sort by price or by title in ASC or DESC order")
    public List<ProductResponseDto> findAll(@Parameter(description = "Number of product on page",
            example = "20", schema = @Schema(type = "integer", defaultValue = "20"))
                                                @RequestParam(defaultValue = "20") Integer count,
                                            @Parameter(description =
                                                    "Number of page", example = "0",
                                                    schema = @Schema(type =
                                                            "integer", defaultValue = "0"))
                                                @RequestParam(defaultValue = "0") Integer page,
                                            @Parameter(description = "By what parameter will "
                                                    + "the sorting be", example = "id",
                                                    schema = @Schema(type
                                                            = "String", defaultValue = "id"))
                                                @RequestParam(defaultValue = "id") String sortBy) {
        Sort sort = Sort.by(sortUtil.getSort(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest).stream()
                .map(dtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @Operation(summary = "get all products where price "
            + "is between two values received as a RequestParam inputs "
            + "and with pagination and ability to sort by price or by title in ASC or DESC order")
    public List<ProductResponseDto> findAllByPriceBetween(@RequestParam BigDecimal from,
                                                          @RequestParam BigDecimal to,
                                                          @Parameter(description
                                                                  = "Number of products on page",
                                                                  example = "20",
                                                                  schema = @Schema(type
                                                                          = "integer",
                                                                          defaultValue = "20"))
                                                              @RequestParam(defaultValue
                                                                      = "20") Integer count,
                                                          @Parameter(description
                                                                  = "Number of page", example = "0",
                                                                  schema
                                                                          = @Schema(type
                                                                          = "integer",
                                                                          defaultValue = "0"))
                                                              @RequestParam(defaultValue = "0")
                                                              Integer page,
                                                          @Parameter(description =
                                                                  "By what parameter will "
                                                                  + "the sorting be",
                                                                  example = "id",
                                                                  schema =
                                                                  @Schema(type = "String",
                                                                          defaultValue = "id"))
                                                              @RequestParam(defaultValue
                                                                      = "id") String sortBy) {
        Sort sort = Sort.by(sortUtil.getSort(sortBy));
        Pageable pageable = PageRequest.of(page, count, sort);
        return productService.findAllByPriceBetween(from, to, pageable).stream()
                .map(dtoMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
