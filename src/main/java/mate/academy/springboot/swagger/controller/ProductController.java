package mate.academy.springboot.swagger.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.dto.response.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.SortParserService;
import mate.academy.springboot.swagger.service.mapper.DtoMapper;
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
@Tag(name = "Products API")
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final DtoMapper<Product, ProductResponseDto, ProductRequestDto> dtoDtoMapper;
    private final SortParserService sortParserService;

    public ProductController(ProductService productService, DtoMapper<Product,
            ProductResponseDto, ProductRequestDto> dtoDtoMapper,
                             SortParserService sortParserService) {
        this.productService = productService;
        this.dtoDtoMapper = dtoDtoMapper;
        this.sortParserService = sortParserService;
    }

    @PostMapping
    @Operation(summary = "Create new product", description = "Returns added product with id")
    public ProductResponseDto add(@RequestBody
                                  @Parameter(name = "ProductRequestDto",
                                          description = "Accept product Dto")
                                  ProductRequestDto productRequestDto) {
        Product product = productService.save(dtoDtoMapper.mapToModel(productRequestDto));
        return dtoDtoMapper.mapToDto(product);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by id", description = "Find product by id")
    public ProductResponseDto get(@PathVariable
                                  @Parameter(name = "id", description = "Product id") Long id) {
        return dtoDtoMapper.mapToDto(productService.get(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update product", description = "Update product by id")
    public ProductResponseDto update(@PathVariable
                                     @Parameter(name = "id", description = "Product id") Long id,
                                     @RequestBody
                                     @Parameter(name = "ProductRequestDto", description =
                                             "Product Dto") ProductRequestDto productRequestDto) {
        Product product = dtoDtoMapper.mapToModel(productRequestDto);
        product.setId(id);
        return dtoDtoMapper.mapToDto(productService.update(product));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product", description = "Delete product by id")
    public void delete(@PathVariable @Parameter(name = "id", description = "Product id") Long id) {
        productService.delete(id);
    }

    @GetMapping
    @Operation(summary = "FindAll", description = "Find all products")
    public List<ProductResponseDto> findAll(@RequestParam(defaultValue = "3")
                                            @Parameter(name = "Count", description =
                                                    "Values number at page (default value is 3)")
                                            Integer count,
                                            @RequestParam(defaultValue = "0")
                                            @Parameter(name = "Page", description =
                                                    "Default first page is 0") Integer page,
                                            @RequestParam(defaultValue = "id")
                                            @Parameter(name = "Sort By", description =
                                                    "Sort by parameter (default parameter is id)")
                                            String sortBy) {
        PageRequest pageRequest = sortParserService.createPageRequest(count, page, sortBy);
        return productService.findAll(pageRequest).stream()
                .map(dtoDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/price")
    @Operation(summary = "Get all products between price from-t")
    public List<ProductResponseDto> findAllPriceBetween(
            @RequestParam(defaultValue = "0")
            @Parameter(name = "MinPrice", description = "Min price") BigDecimal from,
            @RequestParam(defaultValue = "1000")
            @Parameter(name = "MaxPrice", description = "Max price") BigDecimal to,
            @RequestParam(defaultValue = "3")
            @Parameter(name = "Count", description =
                    "Values number at page (default value is 3)") Integer count,
            @RequestParam(defaultValue = "0")
            @Parameter(name = "Page", description = "Default first page is 0") Integer page,
            @RequestParam(defaultValue = "id")
            @Parameter(name = "Sort By", description =
                    "Sort by parameter (default parameter is id)") String sortBy) {
        PageRequest pageRequest = sortParserService.createPageRequest(count, page, sortBy);
        return productService.findAllByPriceBetween(from, to, pageRequest)
                .stream()
                .map(dtoDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
