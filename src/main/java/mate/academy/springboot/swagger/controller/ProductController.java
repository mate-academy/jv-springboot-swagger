package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import mate.academy.springboot.swagger.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.dto.response.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.SortParserService;
import mate.academy.springboot.swagger.service.mapper.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Tag(name = "Products API")
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final DtoMapper<Product, ProductResponseDto, ProductRequestDto> dtoDtoMapper;
    private final SortParserService sortParserService;

    @Autowired
    public ProductController(ProductService productService, DtoMapper<Product,
            ProductResponseDto, ProductRequestDto> dtoDtoMapper, SortParserService sortParserService) {
        this.productService = productService;
        this.dtoDtoMapper = dtoDtoMapper;
        this.sortParserService = sortParserService;
    }

    @PostMapping
    @Operation(summary = "Create new product", description = "Returns added product with id")
    public ProductResponseDto add(@RequestBody @Parameter(name = "ProductRequestDto", description = "Accept product Dto") ProductRequestDto productRequestDto) {
        Product product = productService.save(dtoDtoMapper.mapToModel(productRequestDto));
        return dtoDtoMapper.mapToDto(product);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by id", description = "Find product by id")
    public ProductResponseDto get(@PathVariable @Parameter(name = "id", description = "Product id") Long id) {
        return dtoDtoMapper.mapToDto(productService.get(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update product", description = "Update product by id")
    public ProductResponseDto update(@PathVariable @Parameter(name = "id", description = "Product id") Long id,
                                     @RequestBody @Parameter(name = "ProductRequestDto", description = "Product Dto") ProductRequestDto productRequestDto) {
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
    public List<ProductResponseDto> findAll(@RequestParam(defaultValue = "3") @Parameter(name = "Count", description = "Values number at page (default value is 3)") Integer count,
                                            @RequestParam(defaultValue = "0") @Parameter(name = "Page", description = "Default first page is 0") Integer page,
                                            @RequestParam(defaultValue = "id") @Parameter(name = "Sort By", description = "Sort by parameter (default parameter is id)") String sortBy) {
        List<Sort.Order> orders = sortParserService.orders(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, Sort.by(orders));
        return productService.findAll(pageRequest).stream()
                .map(dtoDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/price")
    @ApiOperation(value = "Get all products between price from-t")
    public List<ProductResponseDto> findAllPriceBetween(
            @RequestParam(defaultValue = "0")
            @ApiParam(value = "default value is 0") BigDecimal from,
            @RequestParam(defaultValue = "10000")
            @ApiParam(value = "default value is ‘10000’") BigDecimal to,
            @RequestParam(defaultValue = "10")
            @ApiParam(value = "default value is 10") Integer count,
            @RequestParam(defaultValue = "0")
            @ApiParam(value = "default value is 0") Integer page,
            @RequestParam(defaultValue = "id")
            @ApiParam(value = "default value is id") String sortBy) {
        List<Sort.Order> orders = sortParserService.orders(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, Sort.by(orders));
        return productService.findAllByPriceBetween(from, to, pageRequest)
                .stream()
                .map(dtoDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/test")
    public String test() {
        productService.save(new Product("iPhone A", new BigDecimal("200")));
        productService.save(new Product("iPhone B", new BigDecimal("500")));
        productService.save(new Product("iPhone C", new BigDecimal("100")));
        productService.save(new Product("iPhone D", new BigDecimal("150")));
        productService.save(new Product("iPhone E", new BigDecimal("900")));
        productService.save(new Product("iPhone F", new BigDecimal("1000")));
        productService.save(new Product("iPhone G", new BigDecimal("300")));
        productService.save(new Product("iPhone H", new BigDecimal("999")));
        productService.save(new Product("iPhone I", new BigDecimal("332")));
        productService.save(new Product("iPhone J", new BigDecimal("450")));
        return "Done";
    }
}
