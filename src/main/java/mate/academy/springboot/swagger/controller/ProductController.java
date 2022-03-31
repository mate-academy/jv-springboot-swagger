package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.model.dto.ProductRequestDto;
import mate.academy.springboot.swagger.model.dto.ProductResponseDto;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.SortService;
import org.modelmapper.ModelMapper;
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
    public static final String DEFAULT_VALUES = "default value is`10`";
    public static final String DEFAULT_PAGE = "default page is `0`";
    public static final String DEFAULT_SORT = "default sort by ID ASC";
    public static final String MIN_PRICE = "default min price all products";
    public static final String MAX_PRICE = "default max price all products";

    private final ProductService productService;
    private final ModelMapper mapper;
    private final SortService sortService;

    public ProductController(ProductService productService,
                             ModelMapper mapper,
                             SortService sortService) {
        this.productService = productService;
        this.mapper = mapper;
        this.sortService = sortService;
    }

    @PostMapping
    @ApiOperation(value = "Create a new product")
    public ProductResponseDto createProduct(@RequestBody ProductRequestDto dto) {
        return mapper.map(productService.create(mapper.map(dto, Product.class)),
                ProductResponseDto.class);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by ID")
    public ProductResponseDto getProductById(@PathVariable Long id) {
        return mapper.map(productService.getById(id), ProductResponseDto.class);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete product by ID")
    public void deleteProductById(@PathVariable Long id) {
        productService.delete(id);
    }

    @PatchMapping
    @ApiOperation(value = "Update product")
    public ProductResponseDto updateProduct(@RequestBody ProductRequestDto dto) {
        return mapper.map(productService.create(mapper.map(dto, Product.class)),
                ProductResponseDto.class);
    }

    @GetMapping
    @ApiOperation(value = "Get all products pageable")
    public List<ProductResponseDto> findAll(@RequestParam (defaultValue = "10")
                                                @ApiParam(value = DEFAULT_VALUES)
                                                        Integer count,
                                            @RequestParam (defaultValue = "0")
                                                @ApiParam(value = DEFAULT_PAGE)
                                                    Integer page,
                                            @RequestParam (defaultValue = "id")
                                                @ApiParam(value = DEFAULT_SORT)
                                                        String sortBy) {

        return productService.getAll(PageRequest.of(page, count, sortService.getSort(sortBy)))
                .stream()
                .map(p -> mapper.map(p, ProductResponseDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    public List<ProductResponseDto> findAllByPrice(@RequestParam (defaultValue = "10")
                                                       @ApiParam(value = DEFAULT_VALUES)
                                                               Integer count,
                                                   @RequestParam (defaultValue = "0")
                                                       @ApiParam(value = DEFAULT_PAGE)
                                                           Integer page,
                                                   @RequestParam (defaultValue = "id")
                                                       @ApiParam(value = DEFAULT_SORT)
                                                               String sortBy,
                                                   @RequestParam (required = false)
                                                       @ApiParam(value = MIN_PRICE)
                                                               BigDecimal from,
                                                   @RequestParam (required = false)
                                                       @ApiParam(value = MAX_PRICE)
                                                               BigDecimal to) {
        return productService.getAllByPrice(from, to,
                        PageRequest.of(page, count, sortService.getSort(sortBy)))
                .stream()
                .map(p -> mapper.map(p, ProductResponseDto.class))
                .collect(Collectors.toList());
    }
}
